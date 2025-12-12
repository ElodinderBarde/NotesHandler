package ch.elodin.project.NotesHandler.mapper.notes;

import ch.elodin.project.NotesHandler.dto.notes.*;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesLink;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorldNotesNoteMapper {

    // READ DTO
    @Mapping(target = "folderId", source = "folder.id")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "linkIds", expression = "java(mapLinks(entity.getLinks()))")
    NoteReadDTO toReadDTO(WorldNotesNote entity);


    @Mapping(target = "folderId", source = "folder.id")
    NoteListDTO toListDTO(WorldNotesNote entity);

    List<NoteListDTO> toListDTOs(List<WorldNotesNote> entities);

    // WRITE DTO → ENTITY
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "folder", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "links", ignore = true)

    // Wichtig: BaseEntity-Felder ignorieren!
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    WorldNotesNote toEntity(NoteWriteDTO dto);

    // UPDATE
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "folder", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "links", ignore = true)

    // BaseEntity-Felder ignorieren
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntityFromDTO(NoteWriteDTO dto, @MappingTarget WorldNotesNote entity);


    @Mapping(target = "id", source = "folder.id")
    @Mapping(target = "folderId", source = "folder.id")
    NoteMoveDTO toNoteMoveDTO(WorldNotesFolder folder);


    @Mapping(target = "title", source = "title")
    NoteRenameDTO toNoteRenameDTO(WorldNotesNote folder);

    // Helper
    default List<Long> mapLinks(List<WorldNotesLink> links) {
        return links == null ? List.of() :
                links.stream().map(WorldNotesLink::getId).toList();
    }



    @Mapping(target = "title", source = "title")
    @Mapping(target ="folderId", ignore = true)
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "linkIds", ignore = true)
    NoteReadDTO toReadDto(WorldNotesNote note);
}
