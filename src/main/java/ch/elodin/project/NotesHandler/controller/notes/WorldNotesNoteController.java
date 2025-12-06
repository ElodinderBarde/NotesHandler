package ch.elodin.project.NotesHandler.controller.notes;

import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesNoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import ch.elodin.project.NotesHandler.service.notes.WorldNotesNoteService;
import ch.elodin.project.NotesHandler.dto.notes.NoteListDTO;
import ch.elodin.project.NotesHandler.dto.notes.NoteReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.NoteWriteDTO;
import org.springframework.http.ResponseEntity;



@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class WorldNotesNoteController {

    private final WorldNotesNoteService noteService;


    @PutMapping("/{id}")
    public ResponseEntity<NoteReadDTO> update(
            @PathVariable Long id,
            @RequestBody NoteWriteDTO dto) {

        return ResponseEntity.ok(noteService.updateNote(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<NoteListDTO>> getAll() {
        return ResponseEntity.ok(noteService.getNotes());
    }

    @PostMapping
    public ResponseEntity<NoteReadDTO> create(@RequestBody NoteWriteDTO dto) {
        return ResponseEntity.ok(noteService.create(dto));
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
}
