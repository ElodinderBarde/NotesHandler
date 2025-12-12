package ch.elodin.project.NotesHandler.service.notes;

import ch.elodin.project.NotesHandler.Repository.AppUserRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesCategoryRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesFolderRepository;
import ch.elodin.project.NotesHandler.repository.notes.WorldNotesNoteRepository;
import ch.elodin.project.NotesHandler.dto.notes.*;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesCategory;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesNoteMapper;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorldNotesNoteService {

    private final WorldNotesNoteRepository noteRepository;
    private final WorldNotesFolderRepository folderRepository;
    private final WorldNotesCategoryRepository categoryRepository;
    private final AppUserRepository userRepository;
    private final WorldNotesNoteMapper mapper;

    private AppUser getCurrentUser() {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found: " + username));
    }


    // ----------------------------------------------------------------------
    // CREATE
    // ----------------------------------------------------------------------
    public NoteReadDTO create(NoteWriteDTO dto) {
        AppUser user = getCurrentUser();

        WorldNotesNote note = mapper.toEntity(dto);
        note.setUser(user);
        note.setCreatedAt(Instant.now());
        note.setUpdatedAt(Instant.now());
        note.setVersion(0L);

        // Folder setzen
        if (dto.folderId() != null) {
            WorldNotesFolder folder = folderRepository.findById(dto.folderId())
                    .orElseThrow(() -> new RuntimeException("Folder not found"));
            note.setFolder(folder);
        }

        // Kategorie setzen
        if (dto.categoryId() != null) {
            WorldNotesCategory category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            note.setCategory(category);
        }else{
        note.setCategory(categoryRepository.findById(1L).orElse(null));

        }

        noteRepository.save(note);
        return mapper.toReadDTO(note);
    }

    // ----------------------------------------------------------------------
    // UPDATE
    // ----------------------------------------------------------------------
    public NoteReadDTO updateNote(Long id, NoteWriteDTO dto) {
        AppUser user = getCurrentUser();

        WorldNotesNote note = noteRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Titel/Content
        if (dto.title() != null) note.setTitle(dto.title());
        if (dto.content() != null) note.setContent(dto.content());

        // Folder
        if (dto.folderId() != null) {
            WorldNotesFolder folder = folderRepository.findById(dto.folderId())
                    .orElseThrow(() -> new RuntimeException("Folder not found"));
            note.setFolder(folder);
        }

        // Kategorie
        if (dto.categoryId() != null) {
            WorldNotesCategory category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            note.setCategory(category);
        }

        note.setUpdatedAt(Instant.now());
        noteRepository.save(note);

        return mapper.toReadDTO(note);
    }

    // ----------------------------------------------------------------------
    // LIST BY FOLDER
    // ----------------------------------------------------------------------
    public List<NoteListDTO> getAllInFolder(Long folderId) {
        AppUser user = getCurrentUser();

        WorldNotesFolder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new RuntimeException("Folder not found"));

        return mapper.toListDTOs(
                noteRepository.findByFolderAndUserId(folder, user.getId())
        );
    }

    // ----------------------------------------------------------------------
    // DELETE
    // ----------------------------------------------------------------------
    public void delete(Long id) {
        AppUser user = getCurrentUser();

        WorldNotesNote note = noteRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Note not found"));

        noteRepository.delete(note);
    }

    public List<NoteListDTO> getAllNotes() {
        AppUser user = getCurrentUser();
        return mapper.toListDTOs(
                noteRepository.findByUserId(user.getId())
        );
    }


    @Transactional
    public @Nullable NoteMoveDTO moveNote(Long noteId, NoteMoveDTO dto) {
        AppUser user = getCurrentUser();

        WorldNotesNote note = noteRepository.findByIdAndUserId(noteId, user.getId())
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (dto.folderId() != null) {
            WorldNotesFolder newFolder = folderRepository.findById(dto.folderId())
                    .orElseThrow(() -> new RuntimeException("Folder not found"));
            note.setFolder(newFolder);
        } else {
            note.setFolder(null);
        }

        note.setUpdatedAt(Instant.now());
        WorldNotesNote savedNote = noteRepository.save(note);

    return mapper.toNoteMoveDTO(savedNote.getFolder());
    }


    @Transactional
    public @Nullable NoteRenameDTO renameNote(Long noteId, NoteRenameDTO dto) {
        AppUser user = getCurrentUser();

        WorldNotesNote note = noteRepository.findByIdAndUserId(noteId, user.getId())
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (dto.title() != null && !dto.title().isBlank()) {
            note.setTitle(dto.title());


            note.setUpdatedAt(Instant.now());
            WorldNotesNote savedNote = noteRepository.save(note);


            return mapper.toNoteRenameDTO(savedNote);
        }
        throw new RuntimeException("Title cannot be null or blank");

    }

    public @Nullable NoteReadDTO getById(Long id){
        AppUser user = getCurrentUser();

        WorldNotesNote note = noteRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Note not found"));

        return mapper.toReadDTO(note);
    }

    public  Optional<NoteReadDTO> findByTitleAndUser(String title) {
        AppUser user = getCurrentUser();

        return noteRepository
                .findByTitleAndUser(title, user)
                .map(mapper::toReadDto);
    }








}