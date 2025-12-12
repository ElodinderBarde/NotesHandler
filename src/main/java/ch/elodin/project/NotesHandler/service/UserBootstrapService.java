package ch.elodin.project.NotesHandler.service;

import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesCategoryRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesFolderRepository;
import ch.elodin.project.NotesHandler.repository.notes.WorldNotesNoteRepository;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesCategory;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesFolder;
import ch.elodin.project.NotesHandler.entity.notes.WorldNotesNote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBootstrapService {

    private final WorldNotesCategoryRepository categoryRepo;
    private final WorldNotesFolderRepository folderRepo;
    private final WorldNotesNoteRepository noteRepo;

    public void initializeDefaultData(AppUser user) {

        // Kategorie
        WorldNotesCategory category =
                categoryRepo.findByNameAndUser("Allgemein", user)
                        .orElseGet(() -> {
                            WorldNotesCategory c = new WorldNotesCategory();
                            c.setName("Allgemein");
                            c.setUser(user);
                            return categoryRepo.save(c);
                        });

        // Root-Folder
        WorldNotesFolder root =
                folderRepo.findByNameAndUser("Root", user)
                        .orElseGet(() -> {
                            WorldNotesFolder f = new WorldNotesFolder();
                            f.setName("Root");
                            f.setUser(user);
                            f.setCategory(category);
                            return folderRepo.save(f);
                        });

        // Begrüßungsnotiz (nur wenn User keine Notes hat)
        if (noteRepo.findAllByUser(user).isEmpty()) {
            WorldNotesNote note = new WorldNotesNote();
            note.setTitle("Willkommen in NotesHandler");
            note.setContent("Dies ist deine persönliche erste Notiz!");
            note.setUser(user);
            note.setFolder(root);
            noteRepo.save(note);
        }
    }
}
