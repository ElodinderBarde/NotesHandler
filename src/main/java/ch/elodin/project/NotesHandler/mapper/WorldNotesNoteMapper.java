package ch.elodin.project.NotesHandler.mapper;


import ch.elodin.project.NotesHandler.dto.notes.NoteListDTO;
import ch.elodin.project.NotesHandler.dto.notes.NoteReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.NoteWriteDTO;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface WorldNotesNoteMapper {

    // ENTITY → LIST DTO
    @Mapping(source = "folder.id", target = "folderId")
    NoteListDTO toListDTO(WorldNotesNote note);

    // ENTITY → READ DTO
    @Mapping(source = "folder.id", target = "folderId")
    @Mapping(
            target = "categories",
            expression = "java(note.getCategories().stream().map(c -> c.getName()).toList())"
    )
    @Mapping(
            target = "outgoingLinks",
            expression = "java(note.getOutgoingLinks().stream().map(l -> l.getTargetNote().getSlug()).toList())"
    )
    @Mapping(
            target = "incomingLinks",
            expression = "java(note.getIncomingLinks().stream().map(l -> l.getSourceNote().getSlug()).toList())"
    )
    NoteReadDTO toReadDTO(WorldNotesNote note);

    // WRITE DTO → ENTITY (Teilmapping, Rest macht der Service)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "folder", ignore = true)             // wird im Service gesetzt
    @Mapping(target = "categories", ignore = true)         // wird im Service gesetzt
    @Mapping(target = "outgoingLinks", ignore = true)
    @Mapping(target = "incomingLinks", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    WorldNotesNote toEntity(NoteWriteDTO dto);
}
