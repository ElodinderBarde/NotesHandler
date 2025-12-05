package ch.elodin.project.NotesHandler.mapper.notes;


import ch.elodin.project.NotesHandler.dto.notes.CategoryDTO;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface WorldNotesCategoryMapper {

    CategoryDTO toDTO(WorldNotesCategory category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "user", ignore = true)
    WorldNotesCategory toEntity(CategoryDTO dto);
}
