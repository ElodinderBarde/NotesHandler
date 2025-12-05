package ch.elodin.project.NotesHandler.Repository.notes;

import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorldNotesFolderRepository extends JpaRepository<WorldNotesFolder, Long> {

    List<WorldNotesFolder> findAllByUserId(Long userId);

    Optional<WorldNotesFolder> findByIdAndUserId(Long id, Long userId);

    List<WorldNotesFolder> findAllByParentFolderId(Long parentFolderId);
}
