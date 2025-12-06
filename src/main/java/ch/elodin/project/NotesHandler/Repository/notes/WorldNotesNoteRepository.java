package ch.elodin.project.NotesHandler.Repository.notes;

import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorldNotesNoteRepository extends JpaRepository<WorldNotesNote, Long> {


    List<WorldNotesNote> findByUserId(Long id);

    List<WorldNotesNote> findByFolderAndUserId(WorldNotesFolder folder, Long id);
}
