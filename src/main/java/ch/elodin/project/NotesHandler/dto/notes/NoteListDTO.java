package ch.elodin.project.NotesHandler.dto.notes;


import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NoteListDTO {

    private Long id;
    private String title;
    private Long folderId;
    private String folderName;

    private List<String> categories;

}
