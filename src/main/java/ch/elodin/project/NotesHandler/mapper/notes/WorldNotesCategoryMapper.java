package ch.elodin.project.NotesHandler.mapper.notes;

import ch.elodin.project.NotesHandler.dto.notes.CategoryReadDTO;
import ch.elodin.project.NotesHandler.dto.notes.CategoryWriteDTO;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesCategory;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorldNotesCategoryMapper {

    // ENTITY → DTO
    CategoryReadDTO toReadDTO(WorldNotesCategory entity);
    List<CategoryReadDTO> toReadDTOs(List<WorldNotesCategory> entities);

    // DTO → ENTITY
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    WorldNotesCategory toEntity(CategoryWriteDTO dto);

    // UPDATE
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDTO(CategoryWriteDTO dto, @MappingTarget WorldNotesCategory entity);
}
