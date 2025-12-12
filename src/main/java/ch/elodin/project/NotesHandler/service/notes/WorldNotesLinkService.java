package ch.elodin.project.NotesHandler.service.notes;

import ch.elodin.project.NotesHandler.Repository.AppUserRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesLinkRepository;
import ch.elodin.project.NotesHandler.repository.notes.WorldNotesNoteRepository;
import ch.elodin.project.NotesHandler.dto.notes.LinkReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.LinkWriteDTO;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesLink;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesLinkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldNotesLinkService {

    private final WorldNotesLinkRepository linkRepository;
    private final WorldNotesNoteRepository noteRepository;
    private final AppUserRepository userRepository;
    private final WorldNotesLinkMapper mapper;


    // ---------------------------------------------------------
    // User laden
    // ---------------------------------------------------------
    private AppUser getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }


    // ---------------------------------------------------------
    // CREATE
    // ---------------------------------------------------------
    public LinkReadDTO createLink(Long noteId, LinkWriteDTO dto) {

        AppUser user = getCurrentUser();

        // Note laden + User prüfen
        WorldNotesNote note = noteRepository.findByIdAndUser(noteId, user)
                .orElseThrow(() -> new RuntimeException("Note not found or no access"));

        WorldNotesLink link = mapper.toEntity(dto);
        link.setNote(note);

        // targetNote optional
        if (dto.targetNoteId() != null) {
            WorldNotesNote target = noteRepository.findByIdAndUser(dto.targetNoteId(), user)
                    .orElseThrow(() -> new RuntimeException("Target note not found or no access"));

            link.setTargetNote(target);
        }

        linkRepository.save(link);

        return mapper.toReadDTO(link);
    }


    // ---------------------------------------------------------
    // UPDATE
    // ---------------------------------------------------------
    public LinkReadDTO updateLink(Long id, LinkWriteDTO dto) {

        AppUser user = getCurrentUser();

        WorldNotesLink link = linkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Link not found"));

        // User besitzt die Note → Zugriff prüfen
        if (!link.getNote().getUser().equals(user)) {
            throw new RuntimeException("No permission to edit this link");
        }

        mapper.updateEntityFromDTO(dto, link);

        // targetNote aktualisieren
        if (dto.targetNoteId() != null) {
            WorldNotesNote target = noteRepository.findByIdAndUser(dto.targetNoteId(), user)
                    .orElseThrow(() -> new RuntimeException("Target note not found or no access"));
            link.setTargetNote(target);
        }

        linkRepository.save(link);

        return mapper.toReadDTO(link);
    }


    // ---------------------------------------------------------
    // GET ALL LINKS OF NOTE
    // ---------------------------------------------------------
    public List<LinkReadDTO> getLinksForNote(Long noteId) {
        AppUser user = getCurrentUser();

        WorldNotesNote note = noteRepository.findByIdAndUser(noteId, user)
                .orElseThrow(() -> new RuntimeException("Note not found or no access"));

        return mapper.toReadDTOs(note.getLinks());
    }


    // ---------------------------------------------------------
    // DELETE
    // ---------------------------------------------------------
    public void deleteLink(Long id) {

        AppUser user = getCurrentUser();

        WorldNotesLink link = linkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Link not found"));

        // Zugriff prüfen
        if (!link.getNote().getUser().equals(user)) {
            throw new RuntimeException("No permission to delete this link");
        }

        linkRepository.delete(link);
    }
}
