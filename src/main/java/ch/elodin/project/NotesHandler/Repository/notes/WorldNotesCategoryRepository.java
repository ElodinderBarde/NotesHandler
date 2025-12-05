package ch.elodin.project.NotesHandler.Repository.notes;

import ch.elodin.project.NotesHandler.entity.notes.WorldNotesCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorldNotesCategoryRepository extends JpaRepository<WorldNotesCategory, Long> {

    List<WorldNotesCategory> findAllByUserId(Long userId);

    Optional<WorldNotesCategory> findByIdAndUserId(Long id, Long userId);

    Optional<WorldNotesCategory> findByNameIgnoreCaseAndUserId(String name, Long userId);
}
