package ch.elodin.project.NotesHandler.service.notes;

import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesFolderRepository;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldNotesFolderService {

    private final WorldNotesFolderRepository folderRepository;
    private final ch.elodin.project.NotesHandler.repository.AppUserRepository userRepository;

    public WorldNotesFolder createFolder(String name, Long parentFolderId, Long userId) {

        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        WorldNotesFolder folder = new WorldNotesFolder();
        folder.setName(name);
        folder.setUser(user);

        if (parentFolderId != null) {
            WorldNotesFolder parent = folderRepository.findByIdAndUserId(parentFolderId, userId)
                    .orElseThrow(() -> new IllegalArgumentException("Parent folder not found"));
            folder.setParentFolder(parent);

        }

        return folderRepository.save(folder);
    }


    public WorldNotesFolder getFolder(Long folderId, Long userId) {
        return folderRepository.findByIdAndUserId(folderId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found"));
    }


    public List<WorldNotesFolder> getAllFolders(Long userId) {
        return folderRepository.findAllByUserId(userId);
    }


    public void deleteFolder(Long folderId, Long userId) {
        WorldNotesFolder folder = getFolder(folderId, userId);
        folderRepository.delete(folder);
    }
}
