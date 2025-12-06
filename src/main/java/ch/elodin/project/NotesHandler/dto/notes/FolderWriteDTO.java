package ch.elodin.project.NotesHandler.dto.notes;

import lombok.Data;

public record FolderWriteDTO(
        Long userId,
        Long categoryId,
        Long parentId,
        String name
) {
    public Long getParentId() {
        return parentId;
    }
}
