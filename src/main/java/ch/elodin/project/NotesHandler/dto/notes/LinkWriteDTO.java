package ch.elodin.project.NotesHandler.dto.notes;

public record LinkWriteDTO(
        String url,
        String text,
        Long targetNoteId
) {}
