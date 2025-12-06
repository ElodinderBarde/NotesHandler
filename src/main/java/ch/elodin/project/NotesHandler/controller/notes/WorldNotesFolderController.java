package ch.elodin.project.NotesHandler.controller.notes;

import ch.elodin.project.NotesHandler.dto.notes.FolderReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.FolderTreeDTO;
import ch.elodin.project.NotesHandler.dto.notes.FolderWriteDTO;
import ch.elodin.project.NotesHandler.service.notes.WorldNotesFolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class WorldNotesFolderController {

    private final WorldNotesFolderService service;

    @PostMapping
    public ResponseEntity<FolderReadDTO> create(@RequestBody FolderWriteDTO dto) {
        return ResponseEntity.ok(service.createFolder(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FolderReadDTO> update(
            @PathVariable Long id,
            @RequestBody FolderWriteDTO dto
    ) {
        return ResponseEntity.ok(service.updateFolder(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<FolderReadDTO>> getAll() {
        return ResponseEntity.ok(service.getAllFolders());
    }

    @GetMapping("/tree")
    public ResponseEntity<List<FolderTreeDTO>> getTree() {
        return ResponseEntity.ok(service.getFolderTree());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteFolder(id);
        return ResponseEntity.noContent().build();
    }
}
