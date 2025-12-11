package ch.elodin.project.NotesHandler.controller.notes;

import ch.elodin.project.NotesHandler.dto.notes.*;
import ch.elodin.project.NotesHandler.service.notes.WorldNotesNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class WorldNotesNoteController {

    private final WorldNotesNoteService noteService;

    @PostMapping
    public ResponseEntity<NoteReadDTO> create(@RequestBody NoteWriteDTO dto) {
        return ResponseEntity.ok(noteService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteReadDTO> update(
            @PathVariable Long id,
            @RequestBody NoteWriteDTO dto) {
        return ResponseEntity.ok(noteService.updateNote(id, dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<NoteListDTO>> getAll() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/folder/{folderId}")
    public ResponseEntity<List<NoteListDTO>> getAllInFolder(@PathVariable Long folderId) {
        return ResponseEntity.ok(noteService.getAllInFolder(folderId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NoteRenameDTO> rename(
            @PathVariable Long id,
            @RequestBody NoteRenameDTO dto
    ) {
        return ResponseEntity.ok(noteService.renameNote(id, dto));

        }


        @PatchMapping("/{id}/move")
    public ResponseEntity<NoteMoveDTO> move(
            @PathVariable Long id,
            @RequestBody NoteMoveDTO dto
    ) {
            return ResponseEntity.ok(noteService.moveNote(id, dto));
        }



}
