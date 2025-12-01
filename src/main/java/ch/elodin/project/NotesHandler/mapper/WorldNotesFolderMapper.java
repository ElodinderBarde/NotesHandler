package ch.elodin.project.NotesHandler.mapper;


import ch.elodin.project.NotesHandler.dto.notes.FolderReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.FolderTreeDTO;
import ch.elodin.project.NotesHandler.dto.notes.FolderWriteDTO;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorldNotesFolderMapper {

    @Mapping(source = "parentFolder.id", target = "parentId")
    FolderReadDTO toReadDTO(WorldNotesFolder folder);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parentFolder", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    WorldNotesFolder toEntity(FolderWriteDTO dto);

    @Mapping(target = "children", ignore = true)
    FolderTreeDTO toTreeDTO(WorldNotesFolder folder);
}
