package ch.elodin.project.NotesHandler.config;

import ch.elodin.project.NotesHandler.Repository.AppUserRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesCategoryRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesFolderRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesNoteRepository;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.entity.enums.RoleEnum;
import ch.elodin.project.NotesHandler.entity.notes.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository userRepo;
    private final WorldNotesCategoryRepository categoryRepo;
    private final WorldNotesFolderRepository folderRepo;
    private final WorldNotesNoteRepository noteRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // USER erstellen, falls keiner existiert
        AppUser user = userRepo.findByUsername("admin").orElse(null);
        if (user == null) {
            user = new AppUser();
            user.setUsername("Elodin");
            user.setEmail("Elodin@local");
            user.setPassword(passwordEncoder.encode("123456789"));
            user.setRole(RoleEnum.valueOf("ADMIN"));
            userRepo.save(user);
            System.out.println("Default-User 'admin' erstellt");
        }

        WorldNotesCategory category = (WorldNotesCategory) categoryRepo.findByName("Allgemein").orElse(null);
        if (category == null) {
            category = new WorldNotesCategory();
            category.setName("Allgemein");
            category.setUser(user);
            categoryRepo.save(category);
            System.out.println("✔ Default-Kategorie 'Allgemein' erstellt");
        }

        // Root-Folder erstellen
        WorldNotesFolder root = (WorldNotesFolder) folderRepo.findByNameAndUser("Root", user).orElse(null);
        if (root == null) {
            root = new WorldNotesFolder();
            root.setName("Root");
            root.setUser(user);
            root.setCategory(category);
            folderRepo.save(root);
            System.out.println("✔ Root-Ordner erstellt");
        }

        // Begrüßungsnotiz erstellen
        if (noteRepo.count() == 0) {
            WorldNotesNote note = new WorldNotesNote();
            note.setTitle("Willkommen in NotesHandler");
            note.setContent("Dies ist deine erste Notiz. Viel Erfolg beim Organisieren!\n Diese Notizplattform ist für Markdwon optimiert. Viel Spaß beim Schreiben!\n\n Wenn du zu Beginn Hilfe benötigst, Syntaxhilfen findest du auf [Markdown](www.markdown.de).");
            note.setFolder(root);
            note.setUser(user);
            noteRepo.save(note);
            System.out.println("✔ Begrüßungsnotiz erstellt");
        }
    }
}
