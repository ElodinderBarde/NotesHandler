package ch.elodin.project.NotesHandler.mapper.notes;

import ch.elodin.project.NotesHandler.dto.notes.FolderReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.FolderWriteDTO;
import ch.elodin.project.NotesHandler.dto.notes.NoteListDTO;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorldNotesFolderMapper {

    // ENTITY -> READ DTO
    @Mapping(target = "parentId", source = "parentFolder.id")
    @Mapping(target = "categoryId", source = "category.id")
    FolderReadDTO toReadDTO(WorldNotesFolder entity);

    List<FolderReadDTO> toReadDTOs(List<WorldNotesFolder> entities);

    // WRITE DTO -> ENTITY (Basisdaten)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "parentFolder", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "notes", ignore = true)
    WorldNotesFolder toEntity(FolderWriteDTO dto);



    @Mapping(target = "id", ignore = true)
    @Mapping(target = "folderId", source = "note.folder.id")
    NoteListDTO toListDTO(WorldNotesNote note);


}
