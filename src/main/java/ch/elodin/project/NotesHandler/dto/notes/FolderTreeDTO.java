package ch.elodin.project.NotesHandler.dto.notes;

import java.util.List;

public record FolderTreeDTO(
        Long id,
        String name,
        List<FolderTreeDTO> children,
        List<NoteListDTO> notes
) {
    public FolderTreeDTO(FolderReadDTO readDTO, List<FolderTreeDTO> children, List<NoteListDTO> notes) {
        this(readDTO.id(), readDTO.name(), children, notes );
    }
    public FolderTreeDTO(Long id, String name) {
        this(id, name, new java.util.ArrayList<>(), new java.util.ArrayList<>());
    }

}
