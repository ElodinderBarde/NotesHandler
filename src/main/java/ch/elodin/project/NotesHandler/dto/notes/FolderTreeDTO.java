package ch.elodin.project.NotesHandler.dto.notes;


import java.util.List;

public record FolderTreeDTO(
        Long id,
        String name,
        List<FolderTreeDTO> children
) {

    public FolderTreeDTO(FolderReadDTO readDTO, List<FolderTreeDTO> children) {
        this(
                readDTO.id(),
                readDTO.name(),
                children
        );
    }
}
