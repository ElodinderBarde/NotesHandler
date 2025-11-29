package ch.elodin.project.NotesHandler.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ErrorResponseDTO {

    // Getter und Setter
    private String error;
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String path;

    public ErrorResponseDTO(String error, String message, int status, String path){
        this.error = error;
        this.message = message;
        this.status = status;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

}
