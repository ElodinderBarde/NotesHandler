package ch.elodin.project.NotesHandler.entity.notes;

import ch.elodin.project.NotesHandler.entity.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "worldnotes_folder")
public class WorldNotesFolder extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AppUser user;

    @OneToMany
    @JoinColumn(name = "folder_id")  // FK in Note
    private List<WorldNotesNote> notes = new ArrayList<>();


    @OneToMany(mappedBy = "parentFolder")
    private List<WorldNotesFolder> childrenFolder = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
    private WorldNotesFolder parentFolder;

    public void setParentFolder(WorldNotesFolder parent) {
        if (parent != null && parent.equals(this)) {
            throw new IllegalArgumentException("A folder cannot be its own parent.");
        }
        this.parentFolder = parent;
    }

}
