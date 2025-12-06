
package ch.elodin.project.NotesHandler.entity.notes;


import ch.elodin.project.NotesHandler.entity.AppUser;
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
    private String linkUrl;
    private String linkText;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", nullable = true)
    private WorldNotesNote note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_note_id", nullable = true)
    private WorldNotesNote targetNote;



    public String getDescription() {
        if (linkText != null && !linkText.isEmpty()) {
            return linkText;
        } else {
            return linkUrl;
        }
    }
}

