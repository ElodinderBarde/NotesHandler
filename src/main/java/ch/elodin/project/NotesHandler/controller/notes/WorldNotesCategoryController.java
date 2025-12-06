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

    private final WorldNotesCategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryReadDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<CategoryReadDTO> create(@RequestBody CategoryWriteDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryReadDTO> update(
            @PathVariable Long id,
            @RequestBody CategoryWriteDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
