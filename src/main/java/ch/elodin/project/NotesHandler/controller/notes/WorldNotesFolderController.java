package ch.elodin.project.NotesHandler.controller.notes;


import ch.elodin.project.NotesHandler.dto.notes.FolderReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.FolderWriteDTO;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesFolderMapper;
import ch.elodin.project.NotesHandler.service.notes.WorldNotesFolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class WorldNotesFolderController {

    private final WorldNotesFolderService folderService;
    private final WorldNotesFolderMapper folderMapper;

    @GetMapping
    public List<FolderReadDTO> getFolders(@RequestParam Long userId) {
        return folderService.getAllFolders(userId).stream()
                .map(folderMapper::toReadDTO)
                .toList();
    }

    @PostMapping
    public FolderReadDTO createFolder(@RequestParam Long userId,
                                      @RequestBody FolderWriteDTO dto) {

        var folder = folderService.createFolder(dto.getName(), dto.getParentFolderId(), userId);
        return folderMapper.toReadDTO(folder);
    }

    @DeleteMapping("/{folderId}")
    public ResponseEntity<?> deleteFolder(@PathVariable Long folderId, @RequestParam Long userId) {
        folderService.deleteFolder(folderId, userId);
        return ResponseEntity.noContent().build();
    }
}
