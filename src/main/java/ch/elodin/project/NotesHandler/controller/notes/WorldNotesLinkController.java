package ch.elodin.project.NotesHandler.controller.notes;

import ch.elodin.project.NotesHandler.dto.notes.LinkReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.LinkWriteDTO;
import ch.elodin.project.NotesHandler.service.notes.WorldNotesLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
public class WorldNotesLinkController {

    private final WorldNotesLinkService service;


    @GetMapping("/note/{noteId}")
    public ResponseEntity<List<LinkReadDTO>> getAllForNote(@PathVariable Long noteId) {
        return ResponseEntity.ok(service.getLinksForNote(noteId));
    }

    @PostMapping("/note/{noteId}")
    public ResponseEntity<LinkReadDTO> create(
            @PathVariable Long noteId,
            @RequestBody LinkWriteDTO dto) {

        return ResponseEntity.ok(service.createLink(noteId, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LinkReadDTO> update(
            @PathVariable Long id,
            @RequestBody LinkWriteDTO dto) {

        return ResponseEntity.ok(service.updateLink(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteLink(id);
        return ResponseEntity.noContent().build();
    }
}
