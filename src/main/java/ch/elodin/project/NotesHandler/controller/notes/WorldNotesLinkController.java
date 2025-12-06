package ch.elodin.project.NotesHandler.controller.notes;

import ch.elodin.project.NotesHandler.entity.AppUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import ch.elodin.project.NotesHandler.service.notes.WorldNotesLinkService;
import ch.elodin.project.NotesHandler.dto.notes.LinkDTO;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesLinkMapper;



@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
public class WorldNotesLinkController {

    private final WorldNotesLinkService linkService;

    @PostMapping
    public ResponseEntity<LinkDTO> create(@RequestBody LinkDTO dto) {
        return ResponseEntity.ok(linkService.createLink(dto));
    }

    @GetMapping
    public ResponseEntity<List<LinkDTO>> getAll() {
        return ResponseEntity.ok(linkService.getLinks());
    }
}
