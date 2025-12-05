package ch.elodin.project.NotesHandler.entity.notes;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "worldnotes_note")
@Data
@NoArgsConstructor
public class WorldNotesNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private WorldNotesFolder folder;

    private String title;
    private String slug;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "sourceNote")
    private List<WorldNotesLink> outgoingLinks = new ArrayList<>();

    @OneToMany(mappedBy = "targetNote")
    private List<WorldNotesLink> incomingLinks = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "worldnotes_note_category",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<WorldNotesCategory> categories = new ArrayList<>();
}
