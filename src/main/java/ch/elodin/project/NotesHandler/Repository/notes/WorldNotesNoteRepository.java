package ch.elodin.project.NotesHandler.Repository.notes;

import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorldNotesNoteRepository extends JpaRepository<WorldNotesNote, Long> {

    List<WorldNotesNote> findAllByUserId(Long userId);

    List<WorldNotesNote> findAllByFolderId(Long folderId);

    Optional<WorldNotesNote> findByIdAndUserId(Long id, Long userId);

    // Für Suche / später
    List<WorldNotesNote> findByTitleContainingIgnoreCaseAndUserId(String title, Long userId);
}
