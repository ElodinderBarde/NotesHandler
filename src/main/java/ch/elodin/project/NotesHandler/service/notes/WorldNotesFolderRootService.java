package ch.elodin.project.NotesHandler.service.notes;


import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesFolderRepository;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import ch.elodin.project.NotesHandler.service.UserBootstrapService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WorldNotesFolderRootService {

    private final WorldNotesFolderRepository folderRepository;
    private final UserBootstrapService userBootstrapService;

    @Transactional
    public WorldNotesFolder ensureRootFolder(AppUser user) {
        List<WorldNotesFolder> roots = folderRepository.findByUserAndParentFolderIsNull(user);

        if (!roots.isEmpty()) {
            return roots.get(0);
        }
    userBootstrapService.initializeDefaultData(user);
        WorldNotesFolder root = new WorldNotesFolder();
        root.setUser(user);
        root.setCreatedAt(Instant.now());
        root.setUpdatedAt(Instant.now());
        root.setVersion(0L);

        return folderRepository.save(root);
    }
}