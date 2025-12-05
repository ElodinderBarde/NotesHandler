package ch.elodin.project.NotesHandler.dto.notes;


import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NoteWriteDTO {

    private String title;
    private String content;

    private Long folderId;

    private List<Long> categoryIds;

    private List<String> newCategories;

    private List<String> links;

}
