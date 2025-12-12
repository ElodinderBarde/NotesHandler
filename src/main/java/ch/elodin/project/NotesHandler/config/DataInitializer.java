package ch.elodin.project.NotesHandler.config;

import ch.elodin.project.NotesHandler.Repository.AppUserRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesCategoryRepository;
import ch.elodin.project.NotesHandler.Repository.notes.WorldNotesFolderRepository;
import ch.elodin.project.NotesHandler.repository.notes.WorldNotesNoteRepository;
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

        // DEFAULT USER ---------------------------------------------------------
        AppUser user = userRepo.findByUsername("Elodin").orElse(null);
        if (user == null) {
            user = new AppUser();
            user.setUsername("Elodin");
            user.setEmail("Elodin@local");
            user.setPassword(passwordEncoder.encode("123456789"));
            user.setRole(RoleEnum.ADMIN);
            userRepo.save(user);
            System.out.println("✔ Default-User 'Elodin' erstellt");
        }

        // DEFAULT CATEGORY -----------------------------------------------------
        WorldNotesCategory category =
                categoryRepo.findByNameAndUser("Allgemein", user).orElse(null);

        if (category == null) {
            category = new WorldNotesCategory();
            category.setName("Allgemein");
            category.setUser(user);
            categoryRepo.save(category);
            System.out.println(" Kategorie 'Allgemein' erstellt");
        }

        // DEFAULT ROOT FOLDER --------------------------------------------------
        WorldNotesFolder root =
                folderRepo.findByNameAndUser("Root", user).orElse(null);

        if (root == null) {
            root = new WorldNotesFolder();
            root.setName("Dein Hauptordner");
            root.setUser(user);
            root.setCategory(category);
            folderRepo.save(root);
            System.out.println("✔ Root-Ordner erstellt");
        }

        // DEFAULT WELCOME NOTE -------------------------------------------------
        if (noteRepo.count() == 0) {
            WorldNotesNote note = new WorldNotesNote();
            note.setTitle("Willkommen in NotesHandler");
            note.setContent("""
                Dies ist deine erste Notiz.

                Die Plattform unterstützt **Markdown**.
                Viel Spaß beim Schreiben!


                    # Hello There
                    
                    ## Das ist eine Überschrift
                    
                    Verwalte deine Notizen einfach mit Drag and Drop, sowie dem Kontextmenü bei Rechtsklick auf einen Ordner oder Notiz.\s
                    
                    ---
                    
                    Markdown unterstützt einige verschiede Funktionen
                    
                    ### Codeblöcke
                    
                    ```cpp
                    
                    std::cout << "Ich kann hier auch Codeblöcke verwenden";\s
                    std::endl;\s
                    
                    // ich lasse diesen Code nach 10 zählen
                    
                    for(int i = 0; i<10; ++i){
                    
                        std::cout << "\\nIch kann auf " << i << " zählen!";\s
                    }
                    
                    ```
                    
                    ---
                    
                    
                    ### Optische bearbeitung
                    
                    Einen Codeblock hier zu erkennen ist jedoch nicht ganz einfach
                    
                    aber ich kann auch **Fett** aber auch *kursiv*.\s
                    ---
                    

              weitere  Syntaxhilfe → www.markdown.de
            """);
            note.setFolder(root);
            note.setUser(user);
            noteRepo.save(note);

            System.out.println("✔ Begrüßungsnotiz erstellt");
        }
    }
}
