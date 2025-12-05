package ch.elodin.project.NotesHandler.dto.notes;


import java.time.LocalDateTime;
import java.util.List;

public class NoteReadDTO {
    private Long id;
    private Long folderId;
    private String title;
    private String slug;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<String> categories;      // nur Namen
    private List<String> outgoingLinks;   // target slugs
    private List<String> incomingLinks;   // source slugs

    public NoteReadDTO() {}

    // Getter + Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFolderId() { return folderId; }
    public void setFolderId(Long folderId) { this.folderId = folderId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }

    public List<String> getOutgoingLinks() { return outgoingLinks; }
    public void setOutgoingLinks(List<String> outgoingLinks) { this.outgoingLinks = outgoingLinks; }

    public List<String> getIncomingLinks() { return incomingLinks; }
    public void setIncomingLinks(List<String> incomingLinks) { this.incomingLinks = incomingLinks; }
}
