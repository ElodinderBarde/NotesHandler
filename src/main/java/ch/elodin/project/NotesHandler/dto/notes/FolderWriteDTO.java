package ch.elodin.project.NotesHandler.dto.notes;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FolderWriteDTO {

    private String name;
    private Long parentFolderId;


}
