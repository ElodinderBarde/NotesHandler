package ch.elodin.project.NotesHandler.mapper.notes;

import ch.elodin.project.NotesHandler.dto.notes.*;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorldNotesCategoryMapper {

    // ---------------------------
    // Entity → ReadDTO
    // ---------------------------
    @Mapping(target = "userId", source = "user.id")
    CategoryReadDTO toReadDTO(WorldNotesCategory entity);

    List<CategoryReadDTO> toReadDTOs(List<WorldNotesCategory> entities);


    // ---------------------------
    // Entity → List DTO
    // ---------------------------
    @Mapping(target = "userId", source = "user.id")
    CategoryDTO toDTO(WorldNotesCategory entity);

    List<CategoryDTO> toDTOs(List<WorldNotesCategory> entities);


    // ---------------------------
    // WriteDTO → Entity (User erst im Service gesetzt!)
    // ---------------------------
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    WorldNotesCategory toEntity(CategoryWriteDTO dto);
}
