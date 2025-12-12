package ch.elodin.project.NotesHandler.entity.notes;

import ch.elodin.project.NotesHandler.entity.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "worldnotes_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorldNotesCategory extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorldNotesFolder> folders = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (name == null || name.isBlank()) {
            name = "Allgemein";
        }
    }
}

