package ch.elodin.project.NotesHandler.dto;


import ch.elodin.project.NotesHandler.entity.enums.RoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class RegisterResponseDTO {

    private Long id;
    private String username;
    private String email;
    private String password;
    private RoleEnum role;

}