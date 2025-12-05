package ch.elodin.project.NotesHandler.entity.notes;

import ch.elodin.project.NotesHandler.entity.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "worldnotes_category")
public class WorldNotesCategory extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AppUser user;

    @ManyToMany
    @JoinTable(
            name = "worldnotes_note_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "note_id")
    )
    private List<WorldNotesNote> notes = new ArrayList<>();

}
