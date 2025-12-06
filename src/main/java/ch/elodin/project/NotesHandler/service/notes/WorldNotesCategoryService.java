package ch.elodin.project.NotesHandler.service.notes;

import ch.elodin.project.NotesHandler.Repository.AppUserRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesCategoryRepository;
import ch.elodin.project.NotesHandler.dto.notes.CategoryWriteDTO;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesCategory;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldNotesCategoryService {

    private final WorldNotesCategoryRepository repo;
    private final AppUserRepository userRepo;
    private final WorldNotesCategoryMapper mapper;

    public WorldNotesCategory createCategory(CategoryWriteDTO dto) {

        WorldNotesCategory entity = mapper.toEntity(dto);

        if (dto.userId() != null) {
            entity.setUser(userRepo.findById(dto.userId())
                    .orElseThrow(() -> new RuntimeException("User not found")));
        }

        Instant now = Instant.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setVersion(0L);

        return repo.save(entity);
    }

    public List<WorldNotesCategory> getCategory() {
        return repo.findAll();
    }

    public void deleteCategory(Long id) {
        repo.deleteById(id);
    }
}
