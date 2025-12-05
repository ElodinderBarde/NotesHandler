package ch.elodin.project.NotesHandler.dto.notes;


import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FolderTreeDTO {

    private Long id;
    private String name;
    private Long parentFolderId;

    private List<FolderTreeDTO> childrenFolder = new ArrayList<>();

}
