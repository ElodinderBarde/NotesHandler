package ch.elodin.project.NotesHandler.dto.notes;

public record FolderWriteDTO(
        String name,
        Long parentId,
        Long categoryId
) {
}
