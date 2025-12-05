package ch.elodin.project.NotesHandler.dto.notes;


import java.util.List;

public class NoteWriteDTO {
    private Long folderId;
    private String title;
    private String content;

    private List<String> categories;   // Kategorie-Namen
    private List<String> links;        // slugs wie [[andere-note]]

    public NoteWriteDTO() {}

    public Long getFolderId() { return folderId; }
    public void setFolderId(Long folderId) { this.folderId = folderId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }

    public List<String> getLinks() { return links; }
    public void setLinks(List<String> links) { this.links = links; }
}
