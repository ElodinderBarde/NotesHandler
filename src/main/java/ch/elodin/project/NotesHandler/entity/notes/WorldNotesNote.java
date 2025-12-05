package ch.elodin.project.NotesHandler.entity.notes;

import ch.elodin.project.NotesHandler.entity.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "worldnotes_note")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorldNotesNote extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    private WorldNotesFolder folder;

    @ManyToMany(mappedBy = "notes")
    private List<WorldNotesCategory> categories = new ArrayList<>();

    @OneToMany(mappedBy = "note")
    private List<WorldNotesLink> links = new ArrayList<>();
}
