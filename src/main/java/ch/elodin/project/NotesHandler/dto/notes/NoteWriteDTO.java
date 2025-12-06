package ch.elodin.project.NotesHandler.dto.notes;

import java.util.List;

public record NoteWriteDTO(
        Long folderId,
        Long categoryId,
        List<Long> linkIds,
        String title,
        String content,
        Integer version,
        String createdAt,
        String updatedAt
) {
    public Long getFolderId() {
        return folderId;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
