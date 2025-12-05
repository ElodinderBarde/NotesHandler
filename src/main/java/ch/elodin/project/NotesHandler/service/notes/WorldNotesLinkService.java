package ch.elodin.project.NotesHandler.service.notes;

import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesLinkRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesNoteRepository;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesLink;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldNotesLinkService {

    private final WorldNotesLinkRepository linkRepository;
    private final WorldNotesNoteRepository noteRepository;

    public WorldNotesLink createExternalLink(String url, String linkText, Long userId) {
        WorldNotesLink link = new WorldNotesLink();
        link.setUrl(url);
        link.setLinkText(linkText);
        return linkRepository.save(link);
    }

    public WorldNotesLink createInternalLink(Long sourceNoteId, Long targetNoteId, String linkText, Long userId) {

        WorldNotesNote source = noteRepository.findByIdAndUserId(sourceNoteId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Source note not found"));

        WorldNotesNote target = noteRepository.findByIdAndUserId(targetNoteId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Target note not found"));

        WorldNotesLink link = new WorldNotesLink();
        link.setNote(source);
        link.setTargetNote(target);
        link.setLinkText(linkText);

        return linkRepository.save(link);
    }

    public void deleteLinksForNote(Long noteId) {
        List<WorldNotesLink> links = linkRepository.findAllByNoteId(noteId);
        linkRepository.deleteAll(links);
    }

    public List<WorldNotesLink> getBacklinks(Long noteId) {
        return linkRepository.findAllByTargetNoteId(noteId);
    }
}
