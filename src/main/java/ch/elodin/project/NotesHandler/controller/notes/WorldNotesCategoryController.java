package ch.elodin.project.NotesHandler.controller.notes;

import ch.elodin.project.NotesHandler.dto.notes.*;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesCategoryMapper;
import ch.elodin.project.NotesHandler.service.notes.WorldNotesCategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class WorldNotesCategoryController {

    private final WorldNotesCategoryService categoryService;
    private final WorldNotesCategoryMapper mapper;

    @PostMapping
    public CategoryReadDTO create(@RequestBody CategoryWriteDTO dto) {
        return mapper.toReadDTO(categoryService.createCategory(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity.ok(
                mapper.toDTOs(categoryService.getCategory())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
