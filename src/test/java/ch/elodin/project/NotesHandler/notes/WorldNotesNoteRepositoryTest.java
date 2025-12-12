package ch.elodin.project.NotesHandler.notes;

import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import ch.elodin.project.NotesHandler.repository.notes.WorldNotesNoteRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class WorldNotesNoteRepositoryTest {

    private WorldNotesNoteRepository noteRepository;

    @Test
    void findByTitleAndUser_returnsNote_whenExists() {
        // Arrange
        AppUser user = new AppUser();
        user.setUsername("testuser");

        WorldNotesNote note = new WorldNotesNote();
        note.setTitle("Test");
        note.setContent("Content");
        note.setUser(user);
        note.setCreatedAt(Instant.now());
        note.setUpdatedAt(Instant.now());

        noteRepository.save(note);

        // Act
        Optional<WorldNotesNote> result =
                noteRepository.findByTitleAndUser("Test", user);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Test");
    }

    @Test
    void findByTitleAndUser_returnsEmpty_whenNotFound() {
        // Arrange
        AppUser user = new AppUser();
        user.setUsername("other");

        // Act
        Optional<WorldNotesNote> result =
                noteRepository.findByTitleAndUser("DoesNotExist", user);

        // Assert
        assertThat(result).isEmpty();
    }
}
