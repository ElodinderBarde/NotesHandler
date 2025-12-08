package ch.elodin.project.NotesHandler.Repository.notes;

import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorldNotesFolderRepository extends JpaRepository<WorldNotesFolder, Long> {

    // Alle Folder eines Users
    List<WorldNotesFolder> findAllByUser(AppUser user);

    // Single Folder eines Users
    Optional<WorldNotesFolder> findByIdAndUser(Long id, AppUser user);

    // Kinder eines Parent-Folders
    List<WorldNotesFolder> findByParentFolder(WorldNotesFolder parentFolder);

    // Root-Folder (parentFolder IS NULL)
    List<WorldNotesFolder> findByUserAndParentFolderIsNull(AppUser user);

    // Optional: Name-Suche pro User
    Optional<WorldNotesFolder> findByNameAndUser(String name, AppUser user);

}
