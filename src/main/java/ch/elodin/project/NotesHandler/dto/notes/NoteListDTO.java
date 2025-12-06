package ch.elodin.project.NotesHandler.dto.notes;


import java.time.Instant;

public record NoteListDTO(
        Long id,
        String title,
        Long folderId,
        Instant updatedAt
) {}
