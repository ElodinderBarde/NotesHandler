package ch.elodin.project.NotesHandler.mapper.notes;


import ch.elodin.project.NotesHandler.dto.notes.NoteListDTO;
import ch.elodin.project.NotesHandler.dto.notes.NoteReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.NoteWriteDTO;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {WorldNotesCategoryMapper.class, WorldNotesFolderMapper.class, WorldNotesLinkMapper.class})
public interface WorldNotesNoteMapper {

    // READ
    @Mapping(target = "folderId", source = "folder.id")
    @Mapping(target = "folderName", source = "folder.name")
    @Mapping(target = "categories", source = "categories")
    @Mapping(target = "outgoingLinks", source = "links")
    @Mapping(target = "incomingLinks", ignore = true) // wird im Service gesetzt
    NoteReadDTO toReadDTO(WorldNotesNote note);

    // LIST
    @Mapping(target = "folderId", source = "folder.id")
    @Mapping(target = "folderName", source = "folder.name")
    @Mapping(target = "categories", expression = "java(note.getCategories().stream().map(c -> c.getName()).toList())")
    NoteListDTO toListDTO(WorldNotesNote note);

    // WRITE: Keine automatische Verknüpfung von Folder, Links, Kategorien!
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "folder", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "links", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    WorldNotesNote toEntity(NoteWriteDTO dto);
}

