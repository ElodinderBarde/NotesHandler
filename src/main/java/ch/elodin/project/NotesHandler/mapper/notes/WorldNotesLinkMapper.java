package ch.elodin.project.NotesHandler.mapper.notes;

import ch.elodin.project.NotesHandler.dto.notes.LinkDTO;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesLink;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface WorldNotesLinkMapper {

    @Mapping(target = "noteId", source = "note.id")
    @Mapping(target = "targetNoteId", source = "targetNote.id")
    LinkDTO toDTO(WorldNotesLink link);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "note", ignore = true)
    @Mapping(target = "targetNote", ignore = true)
    WorldNotesLink toEntity(LinkDTO dto);
}

