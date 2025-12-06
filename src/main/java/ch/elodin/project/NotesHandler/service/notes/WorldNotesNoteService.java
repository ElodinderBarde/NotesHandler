package ch.elodin.project.NotesHandler.service.notes;

import ch.elodin.project.NotesHandler.Repository.AppUserRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesCategoryRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesFolderRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesNoteRepository;
import ch.elodin.project.NotesHandler.dto.notes.NoteListDTO;
import ch.elodin.project.NotesHandler.dto.notes.NoteReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.NoteWriteDTO;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesCategory;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesFolderMapper;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesNoteMapper;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldNotesNoteService {

    private final WorldNotesNoteRepository noteRepository;
    private final WorldNotesFolderRepository folderRepository;
    private final WorldNotesCategoryRepository categoryRepository;
    private final AppUserRepository userRepository;
    private final WorldNotesNoteMapper noteMapper;
    private final WorldNotesFolderMapper worldNotesFolderMapper;

    private AppUser getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    public NoteReadDTO createNote(NoteWriteDTO dto) {
        AppUser user = getCurrentUser();

        WorldNotesNote note = noteMapper.toEntity(dto);
        note.setUser(user);

        if (dto.getFolderId() != null) {
            WorldNotesFolder folder = folderRepository.findById(dto.getFolderId())
                    .orElseThrow(() -> new RuntimeException("Folder not found"));
            note.setFolder(folder);
        }

        if (dto.getCategoryId() != null) {
            List<WorldNotesCategory> category =
                    categoryRepository.findAllById(Collections.singleton(dto.getCategoryId()));
            note.setCategory((WorldNotesCategory) category);
        }

        noteRepository.save(note);
        return noteMapper.toReadDTO(note);
    }

    public NoteReadDTO updateNote(Long id, NoteWriteDTO dto) {
        AppUser user = getCurrentUser();

        WorldNotesNote note = noteRepository.findByFolderAndUserId(null, user.getId()).stream()
                        . filter(n -> n.getId().equals(id))
                        . findFirst()
                        .orElseThrow(() -> new RuntimeException("Note not found"));
        noteMapper.updateEntityFromDTO(dto, note);

        // Folder
        if (dto.getFolderId() != null) {
            WorldNotesFolder folder = folderRepository.findById(dto.getFolderId())
                    .orElseThrow(() -> new RuntimeException("Folder not found"));
            note.setFolder(folder);
        }

        // Kategorien
        if (dto.getCategoryId() != null) {
            List<WorldNotesCategory> category =
                    categoryRepository.findAllById(Collections.singleton(dto.getCategoryId()));
            note.setCategory((WorldNotesCategory) category);
        }

        noteRepository.save(note);
        return noteMapper.toReadDTO(note);
    }

    public List<NoteListDTO> getNotes() {
        AppUser user = getCurrentUser();
        return noteMapper.toListDTOs(noteRepository.findByUserId(user.getId()));
    }

    public @Nullable NoteReadDTO create(NoteWriteDTO dto) {
        AppUser user = getCurrentUser();
        WorldNotesNote note = noteMapper.toEntity(dto);
        note.setUser(user);
        return noteMapper.toReadDTO(noteRepository.save(note));
    }

    public @Nullable List<NoteListDTO> getAllInFolder(Long folderId) {
        AppUser user = getCurrentUser();
        WorldNotesFolder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new RuntimeException("Folder not found"));
        return noteMapper.toListDTOs(noteRepository.findByFolderAndUserId(folder, user.getId()));

    }

    public void delete(Long id) {
        AppUser user = getCurrentUser();
        WorldNotesNote note = noteRepository.findByFolderAndUserId(null, user.getId()).stream()
                . filter(n -> n.getId().equals(id))
                . findFirst()
                .orElseThrow(() -> new RuntimeException("Note not found"));
    noteRepository.delete(note);
    }
}
