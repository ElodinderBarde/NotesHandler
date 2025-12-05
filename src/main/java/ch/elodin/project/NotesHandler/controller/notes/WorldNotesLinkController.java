package ch.elodin.project.NotesHandler.controller.notes;

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
    private final WorldNotesLinkMapper linkMapper;

    @GetMapping("/backlinks/{noteId}")
    public List<LinkDTO> getBacklinks(@PathVariable Long noteId) {
        return linkService.getBacklinks(noteId).stream()
                .map(linkMapper::toDTO)
                .toList();


        }
}
