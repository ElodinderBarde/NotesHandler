package ch.elodin.project.NotesHandler.dto.notes;

public record FolderMoveDTO(Long id,
                            Long parentId) {

    public FolderMoveDTO(Long id, Long parentId) {
        this.id = id;
        this.parentId = parentId;
    }

}

