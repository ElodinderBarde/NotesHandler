package ch.elodin.project.NotesHandler.controller.notes;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import ch.elodin.project.NotesHandler.service.notes.WorldNotesNoteService;
import ch.elodin.project.NotesHandler.dto.notes.NoteListDTO;
import ch.elodin.project.NotesHandler.dto.notes.NoteReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.NoteWriteDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;



@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class WorldNotesNoteController {

    private final WorldNotesNoteService noteService;

    // Alle Notes eines Nutzers
    @GetMapping
    public List<NoteListDTO> getAllNotes(@RequestParam Long userId) {
        return noteService.getAllNotes(userId);
    }

    // Einzelne Note
    @GetMapping("/{noteId}")
    public NoteReadDTO getNote(@PathVariable Long noteId, @RequestParam Long userId) {
        return noteService.getNote(noteId, userId);
    }

    // Neue Note
    @PostMapping
    public NoteReadDTO createNote(@RequestBody NoteWriteDTO dto, @RequestParam Long userId) {
        return noteService.createNote(dto, userId);
    }

    // Note aktualisieren
    @PutMapping("/{noteId}")
    public NoteReadDTO updateNote(
            @PathVariable Long noteId,
            @RequestBody NoteWriteDTO dto,
            @RequestParam Long userId
    ) {
        return noteService.updateNote(noteId, dto, userId);
    }

    // Note löschen
    @DeleteMapping("/{noteId}")
    public ResponseEntity<?> deleteNote(
            @PathVariable Long noteId,
            @RequestParam Long userId
    ) {
        noteService.deleteNote(noteId, userId);
        return ResponseEntity.noContent().build();
    }
}
