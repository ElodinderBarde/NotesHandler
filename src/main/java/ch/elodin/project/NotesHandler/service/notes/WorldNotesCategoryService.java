package ch.elodin.project.NotesHandler.service.notes;

import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesCategoryRepository;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldNotesCategoryService {

    private final WorldNotesCategoryRepository categoryRepository;
    private final ch.elodin.project.NotesHandler.repository.AppUserRepository userRepository;

    public WorldNotesCategory createCategory(String name, Long userId) {

        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        categoryRepository.findByNameIgnoreCaseAndUserId(name, userId)
                .ifPresent(cat -> {
                    throw new IllegalArgumentException("Category already exists");
                });

        WorldNotesCategory category = new WorldNotesCategory();
        category.setName(name);
        category.setUser(user);

        return categoryRepository.save(category);
    }


    public List<WorldNotesCategory> getCategories(Long userId) {
        return categoryRepository.findAllByUserId(userId);
    }


    public WorldNotesCategory getCategory(Long id, Long userId) {
        return categoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }


    public void deleteCategory(Long id, Long userId) {
        WorldNotesCategory cat = getCategory(id, userId);
        categoryRepository.delete(cat);
    }
}
