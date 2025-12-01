package ch.elodin.project.NotesHandler.entity.notes;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "worldnotes_category")
public class WorldNotesCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<WorldNotesNote> notes = new ArrayList<>();

    // ----- Constructors -----
    public WorldNotesCategory() {}

    // ----- Getter & Setter -----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<WorldNotesNote> getNotes() { return notes; }
    public void setNotes(List<WorldNotesNote> notes) { this.notes = notes; }
}
