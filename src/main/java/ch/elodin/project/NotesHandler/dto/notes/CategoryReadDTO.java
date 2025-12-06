package ch.elodin.project.NotesHandler.dto.notes;

import java.time.Instant;

public record CategoryReadDTO(
        Long id,
        String name,
        Long userId,
        Instant createdAt,
        Instant updatedAt
) {}
