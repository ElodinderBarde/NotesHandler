package ch.elodin.project.NotesHandler.notes;

import ch.elodin.project.NotesHandler.dto.notes.NoteReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.NoteWriteDTO;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesNoteMapper;
import ch.elodin.project.NotesHandler.repository.notes.WorldNotesNoteRepository;
import ch.elodin.project.NotesHandler.Repository.AppUserRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesFolderRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesCategoryRepository;
import ch.elodin.project.NotesHandler.service.notes.WorldNotesNoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorldNotesNoteServiceTest {

    @Mock
    private WorldNotesNoteRepository noteRepository;

    @Mock
    private WorldNotesFolderRepository folderRepository;

    @Mock
    private WorldNotesCategoryRepository categoryRepository;

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private WorldNotesNoteMapper mapper;

    @InjectMocks
    private WorldNotesNoteService service;

    @Test
    void createNote_successfullyCreatesNote() {
        // Arrange
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername("testuser");

        NoteWriteDTO writeDTO = new NoteWriteDTO(
                25L,
                1L,
                null,
                null,
                "testNote",
                "This is a test note.",
                null


        );

        WorldNotesNote entity = new WorldNotesNote();
        entity.setTitle("Test Note");
        entity.setContent("Test Content");

        NoteReadDTO readDTO = new NoteReadDTO(
                1L,
                "Test Note",
                "Test Content",
                null,
                null,
                null,
                Instant.now(),
                Instant.now()
        );

        when(userRepository.findByUsername(any()))
                .thenReturn(Optional.of(user));

        when(mapper.toEntity(writeDTO))
                .thenReturn(entity);

        when(noteRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        when(mapper.toReadDTO(any()))
                .thenReturn(readDTO);

        // Act
        NoteReadDTO result = service.create(writeDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo("Test Note");
        assertThat(result.content()).isEqualTo("Test Content");

        verify(noteRepository).save(any(WorldNotesNote.class));
    }
}
