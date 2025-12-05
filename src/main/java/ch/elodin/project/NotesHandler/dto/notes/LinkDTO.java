package ch.elodin.project.NotesHandler.dto.notes;


public class LinkDTO {
    private Long id;
    private String sourceSlug;
    private String targetSlug;
    private String linkText;

    public LinkDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSourceSlug() { return sourceSlug; }
    public void setSourceSlug(String sourceSlug) { this.sourceSlug = sourceSlug; }

    public String getTargetSlug() { return targetSlug; }
    public void setTargetSlug(String targetSlug) { this.targetSlug = targetSlug; }

    public String getLinkText() { return linkText; }
    public void setLinkText(String linkText) { this.linkText = linkText; }
}
