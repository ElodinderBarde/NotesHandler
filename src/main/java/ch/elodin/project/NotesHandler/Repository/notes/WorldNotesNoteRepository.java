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



        // alle Notes eines Users
        List<WorldNotesNote> findByUserId(Long userId);
        // Wird vom LinkService und NoteService benötigt
        Optional<WorldNotesNote> findByIdAndUser(Long id, AppUser user);

        // Wird vom NoteService benötigt
        Optional<WorldNotesNote> findByIdAndUserId(Long id, Long userId);

        // Wird für Notes im Folder benötigt
        List<WorldNotesNote> findByFolderAndUserId(WorldNotesFolder folder, Long userId);

        // Wird für "getAll notes" benötigt
        List<WorldNotesNote> findAllByUser(AppUser user);
    }
