// src/components/notes/NoteEditor.jsx
import { useEffect, useState } from "react";
import api from "../../../utils/api";
import MarkdownEditor from "./MarkdownEditor";

export default function NoteEditor({ note, refreshNotes ,    openWikiLink}) {
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    // Wenn eine neue Note ausgewählt wird → Editor aktualisieren
    useEffect(() => {

        if (note) {
            setTitle(note.title || "");
            setContent(note.content || "");

        }

        console.log("Markdown value:", content);
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

            <div className="toolbar">
                <button className="btn-save" onClick={handleSave}>Save</button>
                <button className="btn-delete" onClick={handleDelete}>Delete</button>
            </div>
            <MarkdownEditor
                value={content}
                onChange={setContent}
                onWikiLink={openWikiLink}


            />


        </div>
    );
}
