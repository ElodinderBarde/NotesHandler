
export default function NoteListItem({ note, selected, onClick }) {
    const preview = note.content?.substring(0, 80) + "...";

    return (
        <div
            className={`note-list-item ${selected ? "selected" : ""}`}
            onClick={onClick}
        >
            <div className="note-title">{note.title}</div>

            <div className="note-preview">
                {preview}
            </div>

            <div className="note-date">
                {new Date(note.updatedAt).toLocaleString()}
            </div>
        </div>
    );
}
