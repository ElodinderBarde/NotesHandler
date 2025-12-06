package ch.elodin.project.NotesHandler.Repository.notes;

import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesLink;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorldNotesLinkRepository extends JpaRepository<WorldNotesLink, Long> {

    List<WorldNotesLink> findAllByNoteAndNote_User(WorldNotesNote note, AppUser user);

    Optional<WorldNotesLink> findByIdAndNote_User(Long id, AppUser user);
}
