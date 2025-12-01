
package ch.elodin.project.NotesHandler.entity.notes;


import jakarta.persistence.*;

@Entity
@Table(name = "worldnotes_note_link")
public class WorldNotesLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_link_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "source_note_id",
            referencedColumnName = "note_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_worldnotes_note_link_source")
    )
    private WorldNotesNote sourceNote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "target_note_id",
            referencedColumnName = "note_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_worldnotes_note_link_target")
    )
    private WorldNotesNote targetNote;

    @Column(name = "link_text")
    private String linkText;

    // ----- Constructors -----
    public WorldNotesLink() {}

    // ----- Getter & Setter -----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public WorldNotesNote getSourceNote() { return sourceNote; }
    public void setSourceNote(WorldNotesNote sourceNote) { this.sourceNote = sourceNote; }

    public WorldNotesNote getTargetNote() { return targetNote; }
    public void setTargetNote(WorldNotesNote targetNote) { this.targetNote = targetNote; }

    public String getLinkText() { return linkText; }
    public void setLinkText(String linkText) { this.linkText = linkText; }
}
