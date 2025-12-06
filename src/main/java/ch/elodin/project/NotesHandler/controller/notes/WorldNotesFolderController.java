package ch.elodin.project.NotesHandler.controller.notes;

import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesFolderRepository;
import ch.elodin.project.NotesHandler.dto.notes.FolderReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.FolderTreeDTO;
import ch.elodin.project.NotesHandler.dto.notes.FolderWriteDTO;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesFolderMapper;
import ch.elodin.project.NotesHandler.service.notes.WorldNotesFolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folders")
public class WorldNotesFolderController {

    private final WorldNotesFolderService service;
    private final WorldNotesFolderMapper mapper;

    // CREATE
    @PostMapping
    public ResponseEntity<FolderReadDTO> create(@RequestBody FolderWriteDTO dto) {
        WorldNotesFolder saved = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toReadDTO(saved));

    }


    // GET ALL (flat list)
    @GetMapping("/all")
    public ResponseEntity<List<FolderReadDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // GET TREE
    @GetMapping("/tree")
    public ResponseEntity<List<FolderTreeDTO>> getTree() {
        return ResponseEntity.ok(service.getTree());
    }


    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
