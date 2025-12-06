package ch.elodin.project.NotesHandler.mapper.notes;

import ch.elodin.project.NotesHandler.dto.notes.FolderReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.FolderTreeDTO;
import ch.elodin.project.NotesHandler.dto.notes.FolderWriteDTO;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import org.mapstruct.*;

import java.util.List;



@Mapper(componentModel = "spring")
public interface WorldNotesFolderMapper {

    // CREATE: WriteDTO → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "parentFolder", ignore = true)
    WorldNotesFolder toEntity(FolderWriteDTO dto);


    // READ: Entity → ReadDTO
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "parentId", source = "parentFolder.id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "id", source = "id")
    FolderReadDTO toReadDTO(WorldNotesFolder entity);

    List<FolderReadDTO> toReadDTOs(List<WorldNotesFolder> entities);



    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "children", ignore = true)
    List<FolderTreeDTO> toTreeDTOs(List<WorldNotesFolder> entities);
}

