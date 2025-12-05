
package ch.elodin.project.NotesHandler.entity.notes;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "worldnotes_note_link")
@Entity
public class WorldNotesLink extends BaseEntity {

    @Column(nullable = false)
    private String url;
    private String linkText;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private WorldNotesNote note;

    @ManyToOne(fetch = FetchType.LAZY)
    private WorldNotesNote targetNote;

}

