package ch.elodin.project.NotesHandler.entity.notes;

import ch.elodin.project.NotesHandler.entity.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "worldnotes_folder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorldNotesFolder extends BaseEntity {

    @Column(nullable = true)
    private String name;

    // User
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    // Belongs to a category
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "category_id", nullable = true )
    private WorldNotesCategory category;

    // Notes inside the folder
    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorldNotesNote> notes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private WorldNotesFolder parentFolder;

    @OneToMany(mappedBy = "parentFolder", cascade =  CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<WorldNotesFolder> children = new ArrayList<>();

}
