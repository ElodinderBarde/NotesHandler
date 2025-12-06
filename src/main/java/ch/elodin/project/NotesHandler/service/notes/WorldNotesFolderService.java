package ch.elodin.project.NotesHandler.service.notes;

import ch.elodin.project.NotesHandler.Repository.AppUserRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesCategoryRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesFolderRepository;
import ch.elodin.project.NotesHandler.dto.notes.FolderReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.FolderTreeDTO;
import ch.elodin.project.NotesHandler.dto.notes.FolderWriteDTO;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesCategory;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesFolderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorldNotesFolderService {

    private final WorldNotesFolderRepository folderRepository;
    private final WorldNotesCategoryRepository categoryRepository;
    private final AppUserRepository userRepository;
    private final WorldNotesFolderMapper mapper;

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

        // Parent ändern (inkl. Zirkularitäts-Check)
        if (dto.parentId() != null) {
            WorldNotesFolder newParent = folderRepository.findByIdAndUser(dto.parentId(), user)
                    .orElseThrow(() -> new IllegalArgumentException("Parent folder not found"));

            if (isAncestorOf(folder, newParent)) {
                throw new IllegalArgumentException("Cannot assign a child as parent (circular hierarchy).");
            }

            folder.setParentFolder(newParent);
        } else if (dto.parentId() == null) {
            // explizit zu Root machen
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

        // Rekursiv alle Kinder löschen, um FK-Fehler zu vermeiden
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
    // Queries
    // ----------------------------------------------------

    public List<FolderReadDTO> getAllFolders() {
        AppUser user = getCurrentUser();
        List<WorldNotesFolder> folders = folderRepository.findAllByUser(user);
        return mapper.toReadDTOs(folders);
    }

    public List<FolderTreeDTO> getFolderTree() {
        AppUser user = getCurrentUser();
        List<WorldNotesFolder> all = folderRepository.findAllByUser(user);

        // Root-Folder bestimmen
        List<WorldNotesFolder> roots = new ArrayList<>();
        for (WorldNotesFolder f : all) {
            if (f.getParentFolder() == null) {
                roots.add(f);
            }
        }

        // Baum aufbauen
        List<FolderTreeDTO> tree = new ArrayList<>();
        for (WorldNotesFolder root : roots) {
            tree.add(buildTree(root, all));
        }
        return tree;
    }

    private FolderTreeDTO buildTree(WorldNotesFolder folder, List<WorldNotesFolder> all) {
        List<FolderTreeDTO> children = new ArrayList<>();
        for (WorldNotesFolder f : all) {
            if (folder.equals(f.getParentFolder())) {
                children.add(buildTree(f, all));
            }
        }
        return new FolderTreeDTO(mapper.toReadDTO(folder), children);
    }
}
