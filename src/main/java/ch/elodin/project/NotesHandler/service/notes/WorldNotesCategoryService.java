package ch.elodin.project.NotesHandler.service.notes;

import ch.elodin.project.NotesHandler.Repository.AppUserRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesCategoryRepository;
import ch.elodin.project.NotesHandler.dto.notes.CategoryReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.CategoryWriteDTO;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesCategory;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldNotesCategoryService {

    private final WorldNotesCategoryRepository repo;
    private final AppUserRepository userRepo;
    private final WorldNotesCategoryMapper mapper;

    private AppUser getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    // CREATE
    public CategoryReadDTO create(CategoryWriteDTO dto) {
        AppUser user = getCurrentUser();

        WorldNotesCategory category = mapper.toEntity(dto);
        category.setUser(user);

        repo.save(category);
        return mapper.toReadDTO(category);
    }

    // UPDATE
    public CategoryReadDTO update(Long id, CategoryWriteDTO dto) {
        AppUser user = getCurrentUser();

        WorldNotesCategory category = repo.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        mapper.updateFromDTO(dto, category);

        repo.save(category);
        return mapper.toReadDTO(category);
    }

    // DELETE
    public void delete(Long id) {
        AppUser user = getCurrentUser();

        WorldNotesCategory category = repo.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        repo.delete(category);
    }

    // GET ALL
    public List<CategoryReadDTO> getAll() {
        AppUser user = getCurrentUser();
        return mapper.toReadDTOs(repo.findAllByUser(user));
    }
}
