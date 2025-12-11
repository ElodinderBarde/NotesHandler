package ch.elodin.project.NotesHandler.controller.notes;

import ch.elodin.project.NotesHandler.dto.notes.*;
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


    @PatchMapping("/{id}")
    public ResponseEntity<FolderReadDTO> rename(
            @PathVariable Long id,
            @RequestBody FolderRenameDTO dto
    ) {
        return ResponseEntity.ok(service.renameFolder(id, dto));
    }
    @PatchMapping("/{id}/move")
    public ResponseEntity<FolderReadDTO> move(
            @PathVariable Long id,
            @RequestBody FolderMoveDTO dto
    ) {
        return ResponseEntity.ok(service.moveFolder(id, dto));
    }

}
