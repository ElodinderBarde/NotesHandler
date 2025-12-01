package ch.elodin.project.NotesHandler.dto.notes;


public class NoteListDTO {
    private Long id;
    private String title;
    private String slug;
    private Long folderId;

    public NoteListDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public Long getFolderId() { return folderId; }
    public void setFolderId(Long folderId) { this.folderId = folderId; }
}
