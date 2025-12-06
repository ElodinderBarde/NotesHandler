package ch.elodin.project.NotesHandler.dto.notes;

public record FolderReadDTO(
        Long id,
        String name,
        Long parentId,
        Long categoryId
) {
}
