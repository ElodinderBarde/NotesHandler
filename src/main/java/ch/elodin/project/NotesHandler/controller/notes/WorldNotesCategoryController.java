package ch.elodin.project.NotesHandler.controller.notes;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import java.util.List;
import ch.elodin.project.NotesHandler.service.notes.WorldNotesCategoryService;
import ch.elodin.project.NotesHandler.dto.notes.CategoryDTO;


@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class WorldNotesCategoryController {

    private final WorldNotesCategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getCategories(@RequestParam Long userId) {
        return categoryService.getCategories(userId)
                .stream()
                .map(cat -> new CategoryDTO(cat.getId(), cat.getName()))
                .toList();
    }

    @PostMapping
    public CategoryDTO createCategory(@RequestParam Long userId, @RequestBody CategoryDTO dto) {
        var cat = categoryService.createCategory(dto.getName(), userId);
        return new CategoryDTO(cat.getId(), cat.getName());
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(
            @PathVariable Long categoryId,
            @RequestParam Long userId
    ) {
        categoryService.deleteCategory(categoryId, userId);
        return ResponseEntity.noContent().build();
    }
}
