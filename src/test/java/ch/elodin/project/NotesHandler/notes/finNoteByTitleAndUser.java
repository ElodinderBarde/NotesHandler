package ch.elodin.project.NotesHandler.notes;

import ch.elodin.project.NotesHandler.dto.notes.NoteReadDTO;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import ch.elodin.project.NotesHandler.Repository.AppUserRepository;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesNoteMapper;
import ch.elodin.project.NotesHandler.repository.notes.WorldNotesNoteRepository;
import ch.elodin.project.NotesHandler.service.notes.WorldNotesNoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindNoteByTitleAndUserTest {

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private WorldNotesNoteRepository noteRepository;

    @Mock
    private WorldNotesNoteMapper mapper;

    @Mock
    private WorldNotesNoteService service;

    @Test
    void findByTitleAndUser_returnsNote_whenFound() {
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername("testuser");

        WorldNotesNote note = new WorldNotesNote();
        note.setTitle("WikiTest");

        NoteReadDTO dto = new NoteReadDTO(
                1L,
                "WikiTest",
                "Content",
                null,
                null,
                null,
                Instant.now(),
                Instant.now()
        );

        when(userRepository.findByUsername(any()))
                .thenReturn(Optional.of(user));

        when(noteRepository.findByTitleAndUser("WikiTest", user))
                .thenReturn(Optional.of(note));

        when(mapper.toReadDto(note))
                .thenReturn(dto);

        Optional<NoteReadDTO> result =
                service.findByTitleAndUser("WikiTest");

        assertThat(result).isPresent();
        assertThat(result.get().title()).isEqualTo("WikiTest");
    }
}
