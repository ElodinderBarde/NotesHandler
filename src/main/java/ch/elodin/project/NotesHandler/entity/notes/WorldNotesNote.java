package ch.elodin.project.NotesHandler.entity.notes;

import jakarta.persistence.*;
import lombok.*;
import ch.elodin.project.NotesHandler.entity.AppUser;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "worldnotes_note")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorldNotesNote extends BaseEntity {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    // User
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    // Folder
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "folder_id", nullable = true)
    private WorldNotesFolder folder;

    // Links in the note
    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorldNotesLink> links = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = true)
    private WorldNotesCategory category;

}
