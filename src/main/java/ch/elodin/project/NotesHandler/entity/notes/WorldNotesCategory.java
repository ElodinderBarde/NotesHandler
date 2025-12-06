package ch.elodin.project.NotesHandler.entity.notes;

import ch.elodin.project.NotesHandler.entity.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Entity
@Table(name = "worldnotes_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorldNotesCategory extends BaseEntity {

    @Column(nullable = true) // <- nicht null aus DB entfernen
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private AppUser user;

    @Column(nullable = true)
    private Instant createdAt;

    @Column(nullable = true)
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        if (createdAt == null) createdAt = now;
        if (updatedAt == null) updatedAt = now;
    }

    public void preUpdate() {
        updatedAt = Instant.now();
    }
}

