package ch.elodin.project.NotesHandler.Repository.notes;

import ch.elodin.project.NotesHandler.entity.notes.WorldNotesLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorldNotesLinkRepository extends JpaRepository<WorldNotesLink, Long> {

    @Query("""
        SELECT l FROM WorldNotesLink l
        WHERE l.note.id = :noteId
        AND l.note.user = :user
    """)
    List<WorldNotesLink> findAllByNoteIdAndUser(Long noteId);
}
