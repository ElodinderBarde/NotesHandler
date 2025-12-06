package ch.elodin.project.NotesHandler.service.notes;

import ch.elodin.project.NotesHandler.Repository.AppUserRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesCategoryRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesFolderRepository;
import ch.elodin.project.NotesHandler.dto.notes.FolderReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.FolderTreeDTO;
import ch.elodin.project.NotesHandler.dto.notes.FolderWriteDTO;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesFolderMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorldNotesFolderService {

    private final WorldNotesFolderRepository repo;
    private final AppUserRepository userRepo;
    private final WorldNotesCategoryRepository categoryRepo;
    private final WorldNotesFolderRepository folderRepo;
    private final WorldNotesFolderMapper mapper;

    // -------------------------------------------------------------
    // CREATE
    // -------------------------------------------------------------
    public WorldNotesFolder create(FolderWriteDTO dto) {

        WorldNotesFolder entity = mapper.toEntity(dto);

        // USER
        if (dto.userId() != null) {
            entity.setUser(
                    userRepo.findById(dto.userId())
                            .orElseThrow(() -> new RuntimeException("User not found"))
            );
        }

        // CATEGORY (optional)
        if (dto.categoryId() != null) {
            entity.setCategory(
                    categoryRepo.findById(dto.categoryId())
                            .orElseThrow(() -> new RuntimeException("Category not found"))
            );
        }

        // PARENT (optional)
        if (dto.parentId() != null) {
            WorldNotesFolder parent = folderRepo.findById(dto.parentId())
                    .orElseThrow(() -> new RuntimeException("Parent folder not found"));

            // Zyklusprüfung: entity darf nicht unter einem seiner Nachfahren landen
            if (isDescendantOf(entity, parent)) {
                throw new IllegalArgumentException("A folder cannot be nested inside its own descendant.");
            }

            entity.setParentFolder(parent);
        }


        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        entity.setVersion(0L);

        return repo.save(entity);
    }
    private boolean isDescendantOf(WorldNotesFolder potentialChild, WorldNotesFolder potentialParent) {

        WorldNotesFolder current = potentialParent.getParentFolder();

        while (current != null) {
            if (current.getId().equals(potentialChild.getId())) {
                return true; // Zyklus entdeckt
            }
            current = current.getParentFolder();
        }

        return false;
    }
    // -------------------------------------------------------------
    // GET ALL (Flat List)
    // -------------------------------------------------------------
    public List<FolderReadDTO> getAll() {
        return repo.findAll().stream()
                .map(mapper::toReadDTO)
                .toList();
    }

    // -------------------------------------------------------------
    // GET TREE (Root → Children → Children)
    // -------------------------------------------------------------
    public List<FolderTreeDTO> getTree() {

        // Alle Folders
        List<WorldNotesFolder> all = repo.findAll();

        // Root-Folders (parentFolder == null)
        List<WorldNotesFolder> roots = all.stream()
                .filter(f -> f.getParentFolder() == null)
                .toList();

        // Für jeden root einen rekursiven Tree erzeugen
        return roots.stream()
                .map(root -> buildTree(root, all))
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------------
    // TREE BUILDER (rekursiv)
    // -------------------------------------------------------------
    private FolderTreeDTO buildTree(WorldNotesFolder folder, List<WorldNotesFolder> all) {

        List<FolderTreeDTO> children = all.stream()
                .filter(f -> folder.equals(f.getParentFolder()))
                .map(child -> buildTree(child, all))
                .toList();

        FolderReadDTO readDTO = mapper.toReadDTO(folder);

        return new FolderTreeDTO(
                readDTO.id(),
                readDTO.name(),
                children
        );
    }

    // -------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------
    @Transactional
    public void delete(Long id) {
        WorldNotesFolder folder = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Folder not found"));

        repo.delete(folder);
    }

}
