package ch.elodin.project.NotesHandler.dto.notes;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CategoryDTO {

    private Long id;
    private String name;

}
