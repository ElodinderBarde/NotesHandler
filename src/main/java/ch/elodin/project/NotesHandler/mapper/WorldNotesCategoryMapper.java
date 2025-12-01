package ch.elodin.project.NotesHandler.mapper;


import ch.elodin.project.NotesHandler.dto.notes.CategoryDTO;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface WorldNotesCategoryMapper {

    CategoryDTO toDTO(WorldNotesCategory category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notes", ignore = true)
    WorldNotesCategory toEntity(CategoryDTO dto);
}
