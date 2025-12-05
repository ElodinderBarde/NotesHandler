package ch.elodin.project.NotesHandler.Repository.notes;

import ch.elodin.project.NotesHandler.entity.notes.WorldNotesLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorldNotesLinkRepository extends JpaRepository<WorldNotesLink, Long> {

    List<WorldNotesLink> findAllByNoteId(Long noteId);

    List<WorldNotesLink> findAllByTargetNoteId(Long noteId);
}
