package ch.elodin.project.NotesHandler.dto.notes;


import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;

public record LinkDTO(
        Long id,
        String url,
        String description,
        Long targetNoteId,
        Integer version,
        String createdAt,
        String updatedAt
) {
}
