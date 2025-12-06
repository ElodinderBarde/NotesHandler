package ch.elodin.project.NotesHandler.mapper.notes;

import ch.elodin.project.NotesHandler.dto.notes.LinkDTO;
import ch.elodin.project.NotesHandler.dto.notes.LinkReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.LinkWriteDTO;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesLink;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorldNotesLinkMapper {

    // ---------------------------
    // READ DTO
    // ---------------------------
    @Mapping(target = "noteId", source = "note.id")
    @Mapping(target = "targetNoteId", source = "targetNote.id")
    @Mapping(target = "linkUrl", source = "linkUrl")
    @Mapping(target = "linkText", source = "linkText")
    LinkReadDTO toReadDTO(WorldNotesLink entity);

    List<LinkReadDTO> toReadDTOs(List<WorldNotesLink> entities);


    // ---------------------------
    // WRITE DTO -> ENTITY
    // ---------------------------
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)

    @Mapping(target = "note", ignore = true)
    @Mapping(target = "targetNote", ignore = true)

    @Mapping(target = "linkUrl", source = "url")
    @Mapping(target = "linkText", source = "text")
    WorldNotesLink toEntity(LinkWriteDTO dto);


    // ---------------------------
    // UPDATE
    // ---------------------------
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)

    @Mapping(target = "note", ignore = true)
    @Mapping(target = "targetNote", ignore = true)

    @Mapping(target = "linkUrl", source = "url")
    @Mapping(target = "linkText", source = "text")
    void updateEntityFromDTO(LinkWriteDTO dto, @MappingTarget WorldNotesLink entity);
}
