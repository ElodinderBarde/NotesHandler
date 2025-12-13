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
                            f.setName("Deine Notizen");
                            f.setUser(user);
                            f.setCategory(category);
                            return folderRepo.save(f);
                        });

        // Begrüßungsnotiz (nur wenn User keine Notes hat)
        if (noteRepo.findAllByUser(user).isEmpty()) {
            WorldNotesNote note = new WorldNotesNote();
            note.setTitle("Deine erste Notiz");
            note.setContent("Dies ist deine persönliche erste Notiz!");
            note.setUser(user);
            note.setFolder(root);
            noteRepo.save(note);


            WorldNotesNote note2 = new WorldNotesNote();
            note2.setTitle("Willkommen in NotesHandler");
            note2.setContent("""
Dies ist deine erste Notiz.

Die Plattform unterstützt **Markdown**.
Viel Spaß beim Schreiben!

# Hello There
## Das ist eine Überschrift
Verwalte deine Notizen einfach mit Drag and Drop, sowie dem Kontextmenü bei Rechtsklick auf einen Ordner oder Notiz.

---
Markdown unterstützt einige verschiedene Funktionen

### Codeblöcke
```cpp
std::cout << "Ich kann hier auch Codeblöcke verwenden";
std::endl;
// ich lasse diesen Code nach 10 zählen
for (int i = 0; i < 10; ++i) {
    std::cout << "\\nIch kann auf " << i << " zählen!";
}
```
---
### Optische bearbeitung

Einen Codeblock hier zu erkennen ist jedoch nicht ganz einfach
aber ich kann auch **Fett** aber auch *kursiv*.\s
---
Für weitere  Syntaxhilfe → www.markdown.de
""");
            note2.setFolder(root);
            note2.setUser(user);
            noteRepo.save(note2);



        }
    }
}
