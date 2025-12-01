package ch.elodin.project.NotesHandler.mapper;

import ch.elodin.project.NotesHandler.dto.notes.LinkDTO;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesLink;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface WorldNotesLinkMapper {

    @Mapping(source = "sourceNote.slug", target = "sourceSlug")
    @Mapping(source = "targetNote.slug", target = "targetSlug")
    LinkDTO toDTO(WorldNotesLink link);
}
