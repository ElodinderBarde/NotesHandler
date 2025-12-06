import NoteListItem from "../NoteListItem/NoteListItem.jsx";
import "./NoteList.css";

export default function NoteList({ notes = [], selectedId, onSelect }) {
    return (
        <div className="note-list">
            {notes.length === 0 && (
                <div className="empty">No notes found</div>
            )}

            {notes.map(note => (
                <div
                    key={note.id}
                    className={`note-item ${note.id === selectedId ? "selected" : ""}`}
                    onClick={() => onSelect(note.id)}
                >
                    <h4>{note.title}</h4>
                    <p>{note.preview}</p>
                </div>
            ))}
        </div>
    );
}
