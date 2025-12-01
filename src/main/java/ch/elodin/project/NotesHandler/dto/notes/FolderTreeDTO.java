package ch.elodin.project.NotesHandler.dto.notes;


import java.util.ArrayList;
import java.util.List;

public class FolderTreeDTO {
    private Long id;
    private String name;
    private List<FolderTreeDTO> children = new ArrayList<>();

    public FolderTreeDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<FolderTreeDTO> getChildren() { return children; }
    public void setChildren(List<FolderTreeDTO> children) { this.children = children; }
}
