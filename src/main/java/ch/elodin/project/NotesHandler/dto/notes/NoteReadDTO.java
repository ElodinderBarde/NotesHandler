package ch.elodin.project.NotesHandler.dto.notes;


import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NoteReadDTO {

    private Long id;
    private String title;
    private String content;

    private Long folderId;
    private String folderName;

    private List<CategoryDTO> categories;

    private List<LinkDTO> outgoingLinks;
    private List<LinkDTO> incomingLinks;

    private String createdAt;
    private String updatedAt;

    // Getter/Setter
}

