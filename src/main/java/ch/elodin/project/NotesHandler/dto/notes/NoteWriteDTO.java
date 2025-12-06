package ch.elodin.project.NotesHandler.dto.notes;


public record NoteWriteDTO(
        Long id,
        Long userId,
        Long categoryId,
        Long folderId,
        String title,
        String content,
        Long links
)  {
    public Long getFolderId() {
        return folderId;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
