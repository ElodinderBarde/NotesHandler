package ch.elodin.project.NotesHandler.mapper.notes;


import ch.elodin.project.NotesHandler.dto.notes.FolderReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.FolderTreeDTO;
import ch.elodin.project.NotesHandler.dto.notes.FolderWriteDTO;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorldNotesFolderMapper {

    @Mapping(target = "childrenFolder", ignore = true)
    FolderTreeDTO toTreeDTO(WorldNotesFolder folder);

    @Mapping(target = "parentFolderId", source = "parentFolder.id")
    FolderReadDTO toReadDTO(WorldNotesFolder folder);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "childrenFolder", ignore = true)
    @Mapping(target = "parentFolder", ignore = true)
    WorldNotesFolder toEntity(FolderWriteDTO dto);
}


