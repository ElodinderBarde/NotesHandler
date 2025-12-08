package ch.elodin.project.NotesHandler.service.notes;

import ch.elodin.project.NotesHandler.Repository.AppUserRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesCategoryRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesFolderRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesNoteRepository;
import ch.elodin.project.NotesHandler.dto.notes.*;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesCategory;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesFolderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WorldNotesFolderService {

    private final WorldNotesFolderRepository folderRepository;
    private final WorldNotesCategoryRepository categoryRepository;
    private final AppUserRepository userRepository;
    private final WorldNotesNoteRepository noteRepository;
    private final WorldNotesFolderMapper mapper;
    private final WorldNotesFolderRootService folderRootService;

    // ----------------------------------------------------
    // Helpers
    // ----------------------------------------------------

    private AppUser getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found: " + username));
    }

    private boolean isAncestorOf(WorldNotesFolder ancestor, WorldNotesFolder node) {
        WorldNotesFolder current = node;

        while (current != null) {
            if (current.getId().equals(ancestor.getId())) {
                return true;
            }
            current = current.getParentFolder();
        }
        return false;
    }

    // ----------------------------------------------------
    // CRUD
    // ----------------------------------------------------

    @Transactional
    public FolderReadDTO createFolder(FolderWriteDTO dto) {
        AppUser user = getCurrentUser();

        WorldNotesFolder folder = mapper.toEntity(dto);
        folder.setUser(user);
        folder.setName(dto.name());
        folder.setCreatedAt(Instant.now());
        folder.setUpdatedAt(Instant.now());
        folder.setVersion(0L);

        // Parent setzen (optional)
        if (dto.parentId() != null) {
            WorldNotesFolder parent = folderRepository.findByIdAndUser(dto.parentId(), user)
                    .orElseThrow(() -> new IllegalArgumentException("Parent folder not found"));
            folder.setParentFolder(parent);
        }

        // Kategorie setzen (optional)
        if (dto.categoryId() != null) {
            WorldNotesCategory category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            folder.setCategory(category);
        }

        WorldNotesFolder saved = folderRepository.save(folder);
        return mapper.toReadDTO(saved);
    }

    @Transactional
    public FolderReadDTO updateFolder(Long id, FolderWriteDTO dto) {
        AppUser user = getCurrentUser();

        WorldNotesFolder folder = folderRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found"));

        if (dto.name() != null && !dto.name().isBlank()) {
            folder.setName(dto.name());
        }

        // Kategorie aktualisieren
        if (dto.categoryId() != null) {
            WorldNotesCategory category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            folder.setCategory(category);
        }

        // Parent ändern
        if (dto.parentId() != null) {
            WorldNotesFolder newParent = folderRepository.findByIdAndUser(dto.parentId(), user)
                    .orElseThrow(() -> new IllegalArgumentException("Parent folder not found"));

            if (isAncestorOf(folder, newParent)) {
                throw new IllegalArgumentException("Cannot assign child as parent (circular reference).");
            }

            folder.setParentFolder(newParent);
        } else {
            folder.setParentFolder(null);
        }

        folder.setUpdatedAt(Instant.now());

        WorldNotesFolder saved = folderRepository.save(folder);
        return mapper.toReadDTO(saved);
    }

    @Transactional
    public void deleteFolder(Long id) {
        AppUser user = getCurrentUser();

        WorldNotesFolder root = folderRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found"));

        deleteRecursively(root);
    }

    private void deleteRecursively(WorldNotesFolder folder) {
        List<WorldNotesFolder> children = folderRepository.findByParentFolder(folder);
        for (WorldNotesFolder child : children) {
            deleteRecursively(child);
        }
        folderRepository.delete(folder);
    }

    // ----------------------------------------------------
    // Folder-Tree
    // ----------------------------------------------------


    @Transactional
    public List<FolderReadDTO> getAllFolders() {
        AppUser user = getCurrentUser();
        return mapper.toReadDTOs(folderRepository.findAllByUser(user));
    }


    @Transactional
    public List<FolderTreeDTO> getFolderTree() {

        AppUser user = getCurrentUser();

        folderRootService.ensureRootFolder(user);

        List<WorldNotesFolder> allFolders = folderRepository.findAllByUser(user);
        List<WorldNotesNote> allNotes = noteRepository.findByUserId(user.getId());

        Map<Long, FolderTreeDTO> dtoMap = new HashMap<>();

        for (WorldNotesFolder f : allFolders) {
            dtoMap.put(
                    f.getId(),
                    new FolderTreeDTO(f.getId(), f.getName())
            );
        }

        for (WorldNotesNote note : allNotes) {

            if (note.getFolder() == null) continue;

            FolderTreeDTO folderDTO = dtoMap.get(note.getFolder().getId());

            if (folderDTO != null) {
                folderDTO.notes().add(
                        mapper.toListDTO(note)
                );
            }
        }

        List<FolderTreeDTO> roots = new ArrayList<>();

        for (WorldNotesFolder f : allFolders) {

            FolderTreeDTO currentDto = dtoMap.get(f.getId());

            if (f.getParentFolder() == null) {
                roots.add(currentDto);
            } else {
                FolderTreeDTO parentDto = dtoMap.get(f.getParentFolder().getId());
                if (parentDto != null) {
                    parentDto.children().add(currentDto);
                }
            }
        }

        return roots;
    }

}