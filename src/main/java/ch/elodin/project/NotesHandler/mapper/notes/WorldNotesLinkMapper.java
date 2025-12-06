package ch.elodin.project.NotesHandler.mapper.notes;

import ch.elodin.project.NotesHandler.dto.notes.LinkDTO;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesLink;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorldNotesLinkMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "url",  ignore = true)
    @Mapping(target = "description",   ignore = true)
    @Mapping(target = "targetNoteId",   ignore = true)
    LinkDTO toDTO(WorldNotesLink entity);

    List<LinkDTO> toDTOs(List<WorldNotesLink> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "linkUrl",   ignore = true)
    @Mapping(target = "linkText",    ignore = true)
    @Mapping(target = "note",     ignore = true)
    @Mapping(target = "targetNote",    ignore = true)
    WorldNotesLink toEntity(LinkDTO dto);
}
