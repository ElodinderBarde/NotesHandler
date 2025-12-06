package ch.elodin.project.NotesHandler.Repository.notes;

import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorldNotesFolderRepository extends JpaRepository<WorldNotesFolder, Long> {

    List<WorldNotesFolder> findAllByUser(AppUser user);

    Optional<WorldNotesFolder> findByIdAndUser(Long id, AppUser user);

    List<WorldNotesFolder> findAllRootsByUser(AppUser user);

    Optional<WorldNotesFolder> findByNameAndUser(String name, AppUser user);

    Optional<WorldNotesFolder> findByName(String name);

    Optional<WorldNotesFolder> findByIdAndUserId(Long id, Long userId);


}
