package ch.elodin.project.NotesHandler.dto.notes;


public class FolderWriteDTO {
    private Long parentId;      // optional
    private String name;

    public FolderWriteDTO() {}

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
