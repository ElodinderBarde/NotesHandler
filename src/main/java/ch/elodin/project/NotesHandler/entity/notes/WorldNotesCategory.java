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

    @Column(nullable = true)
    private Instant createdAt;

    @Column(nullable = true)
    private Instant updatedAt;


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorldNotesFolder> folders = new ArrayList<>();


    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        if (createdAt == null) createdAt = now;
        if (updatedAt == null) updatedAt = now;
        if(this.name == null) name = "Allgemein";
    }

    public void preUpdate() {
        updatedAt = Instant.now();
    }
}

