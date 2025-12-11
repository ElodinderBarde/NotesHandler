package ch.elodin.project.NotesHandler.dto.notes;

public record FolderRenameDTO(
     String name
    )


{

    public FolderRenameDTO( String name) {
    this.name = name;
}
}

