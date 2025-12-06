// src/components/notes/NoteEditor.jsx
import { useEffect, useState } from "react";
import api from "../../../utils/api";

export default function NoteEditor({ note, refreshNotes }) {
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    // Wenn eine neue Note ausgewählt wird → Editor aktualisieren
    useEffect(() => {
        if (note) {
            setTitle(note.title || "");
            setContent(note.content || "");
        }
    }, [note]);

    if (!note) {
        return (
            <div className="note-editor empty">
                Select a note to start editing.
            </div>
        );
    }

    async function handleSave() {
        await api.put(`/notes/${note.id}`, {
            title,
            content
        });
        refreshNotes();
    }

    async function handleDelete() {
        await api.delete(`/notes/${note.id}`);
        refreshNotes();
    }

    return (
        <div className="note-editor">
            <input
                className="note-title-input"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
            />

            <textarea
                className="note-content-input"
                value={content}
                onChange={(e) => setContent(e.target.value)}
            />

            <div className="toolbar">
                <button className="btn-save" onClick={handleSave}>Save</button>
                <button className="btn-delete" onClick={handleDelete}>Delete</button>
            </div>
        </div>
    );
}
