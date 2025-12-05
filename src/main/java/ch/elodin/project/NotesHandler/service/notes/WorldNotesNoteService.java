package ch.elodin.project.NotesHandler.service.notes;

import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesCategoryRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesFolderRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesLinkRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesNoteRepository;
import ch.elodin.project.NotesHandler.dto.notes.NoteListDTO;
import ch.elodin.project.NotesHandler.dto.notes.NoteReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.NoteWriteDTO;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesCategory;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesLink;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesLinkMapper;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesNoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorldNotesNoteService {

    private final WorldNotesNoteRepository noteRepository;
    private final WorldNotesCategoryRepository categoryRepository;
    private final WorldNotesFolderRepository folderRepository;
    private final WorldNotesLinkRepository linkRepository;
    private final ch.elodin.project.NotesHandler.repository.AppUserRepository userRepository;

    private final WorldNotesNoteMapper noteMapper;
    private final WorldNotesLinkMapper linkMapper;

    public NoteReadDTO createNote(NoteWriteDTO dto, Long userId) {

        AppUser user = findUser(userId);

        WorldNotesNote note = new WorldNotesNote();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        note.setUser(user);

        // Folder optional
        if (dto.getFolderId() != null) {
            note.setFolder(resolveFolder(dto.getFolderId(), userId));
        }

        // Kategorien (IDs + neue Kategorien)
        note.setCategories(resolveCategories(dto.getCategoryIds(), dto.getNewCategories(), userId));

        // Note zuerst speichern, damit sie eine ID hat
        note = noteRepository.save(note);

        // Links parsen (externe + interne Links)
        List<WorldNotesLink> links = parseMarkdownLinks(dto.getLinks(), note, userId);
        linkRepository.saveAll(links);
        note.setLinks(links);

        return buildNoteDTOWithBacklinks(note);
    }

    public List<NoteListDTO> getAllNotes(Long userId) {
        return noteRepository.findAllByUserId(userId)
                .stream()
                .map(noteMapper::toListDTO)
                .toList();
    }

    // ----------------------------------------
    // UPDATE NOTE
    // ----------------------------------------
    public NoteReadDTO updateNote(Long noteId, NoteWriteDTO dto, Long userId) {

        WorldNotesNote note = getNoteOwned(noteId, userId);

        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());

        if (dto.getFolderId() != null) {
            note.setFolder(resolveFolder(dto.getFolderId(), userId));
        } else {
            note.setFolder(null);
        }

        note.setCategories(resolveCategories(dto.getCategoryIds(), dto.getNewCategories(), userId));

        note = noteRepository.save(note);

        // Links komplett neu generieren
        linkRepository.deleteAll(note.getLinks());
        List<WorldNotesLink> newLinks = parseMarkdownLinks(dto.getLinks(), note, userId);
        linkRepository.saveAll(newLinks);
        note.setLinks(newLinks);

        return buildNoteDTOWithBacklinks(note);
    }


    // ----------------------------------------
    // DELETE NOTE
    // ----------------------------------------
    public void deleteNote(Long noteId, Long userId) {
        WorldNotesNote note = getNoteOwned(noteId, userId);

        // outgoing links löschen
        linkRepository.deleteAll(note.getLinks());

        // incoming links löschen (Backlinks)
        List<WorldNotesLink> incoming = linkRepository.findAllByTargetNoteId(noteId);
        linkRepository.deleteAll(incoming);

        noteRepository.delete(note);
    }


    // ----------------------------------------
    // READ NOTE
    // ----------------------------------------
    public NoteReadDTO getNote(Long noteId, Long userId) {
        WorldNotesNote note = getNoteOwned(noteId, userId);
        return buildNoteDTOWithBacklinks(note);
    }


    // ----------------------------------------
    // PRIVATE HELPERS
    // ----------------------------------------

    private AppUser findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private WorldNotesNote getNoteOwned(Long noteId, Long userId) {
        return noteRepository.findByIdAndUserId(noteId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Note not found for this user"));
    }

    private WorldNotesFolder resolveFolder(Long folderId, Long userId) {
        return folderRepository.findByIdAndUserId(folderId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found"));
    }


    // ----------------------------------------
    // CATEGORY RESOLUTION
    // ----------------------------------------
    private List<WorldNotesCategory> resolveCategories(List<Long> categoryIds,
                                                       List<String> newCategories,
                                                       Long userId) {

        List<WorldNotesCategory> result = new ArrayList<>();

        // vorhandene Kategorien per ID
        if (categoryIds != null) {
            for (Long id : categoryIds) {
                categoryRepository.findByIdAndUserId(id, userId)
                        .ifPresent(result::add);
            }
        }

        // neue Kategorien per Name
        if (newCategories != null) {
            for (String name : newCategories) {

                Optional<WorldNotesCategory> existing =
                        categoryRepository.findByNameIgnoreCaseAndUserId(name, userId);

                if (existing.isPresent()) {
                    result.add(existing.get());
                } else {
                    WorldNotesCategory cat = new WorldNotesCategory();
                    cat.setName(name);
                    cat.setUser(findUser(userId));
                    result.add(categoryRepository.save(cat));
                }
            }
        }

        return result;
    }


    // ----------------------------------------
    // LINK PARSING (Markdown)
    // ----------------------------------------
    private List<WorldNotesLink> parseMarkdownLinks(List<String> links,
                                                    WorldNotesNote note,
                                                    Long userId) {

        if (links == null) return List.of();

        List<WorldNotesLink> result = new ArrayList<>();

        for (String raw : links) {

            WorldNotesLink link = new WorldNotesLink();
            link.setNote(note);

            if (raw.startsWith("[[") && raw.endsWith("]]")) {
                // interner Link
                String title = raw.substring(2, raw.length() - 2);
                resolveInternalLink(note, link, title, userId);

            } else {
                // externer Link
                link.setUrl(raw);
                link.setLinkText(raw);
            }

            result.add(link);
        }

        return result;
    }


    // ----------------------------------------
    // INTERNAL LINK RESOLUTION
    // ----------------------------------------
    private void resolveInternalLink(WorldNotesNote note,
                                     WorldNotesLink link,
                                     String targetTitle,
                                     Long userId) {

        Optional<WorldNotesNote> target =
                noteRepository.findByTitleContainingIgnoreCaseAndUserId(targetTitle, userId)
                        .stream().findFirst();

        target.ifPresent(link::setTargetNote);
        link.setLinkText("[[" + targetTitle + "]]");
    }


    // ----------------------------------------
    // DTO BUILDER INCLUDING BACKLINKS
    // ----------------------------------------
    private NoteReadDTO buildNoteDTOWithBacklinks(WorldNotesNote note) {

        NoteReadDTO dto = noteMapper.toReadDTO(note);

        List<WorldNotesLink> backlinks = linkRepository.findAllByTargetNoteId(note.getId());
        dto.setIncomingLinks(
                backlinks.stream().map(linkMapper::toDTO).toList()
        );

        return dto;
    }
}