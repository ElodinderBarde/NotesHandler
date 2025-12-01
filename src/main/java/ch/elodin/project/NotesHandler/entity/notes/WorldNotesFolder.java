package ch.elodin.project.NotesHandler.entity.notes;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class WorldNotesFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private WorldNotesFolder parentFolder;

    @OneToMany(mappedBy = "parentFolder")
    private List<WorldNotesFolder> children;

    @OneToMany(mappedBy = "folder")
    private List<WorldNotesNote> notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // GETTER & SETTER ↓↓↓

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public WorldNotesFolder getParentFolder() { return parentFolder; }
    public void setParentFolder(WorldNotesFolder parentFolder) { this.parentFolder = parentFolder; }

    public List<WorldNotesFolder> getChildren() { return children; }
    public void setChildren(List<WorldNotesFolder> children) { this.children = children; }

    public List<WorldNotesNote> getNotes() { return notes; }
    public void setNotes(List<WorldNotesNote> notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
