package ch.elodin.project.NotesHandler.service.notes;

import ch.elodin.project.NotesHandler.Repository.AppUserRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesLinkRepository;
import ch.elodin.project.NotesHandler.dto.notes.LinkDTO;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesLink;
import ch.elodin.project.NotesHandler.mapper.notes.WorldNotesLinkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldNotesLinkService {

    private final WorldNotesLinkRepository linkRepository;
    private final AppUserRepository userRepository;
    private final WorldNotesLinkMapper linkMapper;

    private AppUser getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    public LinkDTO createLink(LinkDTO dto) {
        AppUser user = getCurrentUser();

        WorldNotesLink entity = linkMapper.toEntity(dto);


        linkRepository.save(entity);
        return linkMapper.toDTO(entity);
    }

    public List<LinkDTO> getLinks() {
        AppUser user = getCurrentUser();
        return linkMapper.toDTOs(linkRepository.findAllByNoteIdAndUser(user.getId()));
    }
}
