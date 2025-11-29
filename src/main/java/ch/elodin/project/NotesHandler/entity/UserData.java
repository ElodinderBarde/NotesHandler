package ch.elodin.project.NotesHandler.entity;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "user_data")
public class UserData {

    private Long id;
    private String username;
    private String email;
    private String passwordHash;

}
