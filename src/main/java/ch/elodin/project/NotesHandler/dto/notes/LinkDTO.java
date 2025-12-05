package ch.elodin.project.NotesHandler.dto.notes;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LinkDTO {

    private Long id;

    private String linkText;

    private String url;          // optional
    private Long targetNoteId;   // optional

    private Long noteId;         // die Note, in der der Link steht

}
