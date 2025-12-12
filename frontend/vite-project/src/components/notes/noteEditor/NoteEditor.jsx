import { useEffect, useState } from "react";
import api from "../../../utils/api";
import MarkdownEditor from "./MarkdownEditor";

export default function NoteEditor({ note, refreshNotes, openWikiLink }) {
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [isEditMode, setIsEditMode] = useState(true);

    useEffect(() => {
        if (note) {
            setTitle(note.title || "");
            setContent(note.content || "");
            setIsEditMode(false); // beim Wechsel immer Edit starten
        }
    }, [note]);

    if (!note) {
        return <div className="note-editor empty">Select a note to start editing.</div>;
    }

    async function handleSave() {
        await api.put(`/notes/${note.id}`, { title, content });
        refreshNotes();
        setIsEditMode(false); // optional: nach Save automatisch Read-Mode
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
                disabled={!isEditMode}
            />

            <div className="toolbar">
                <button onClick={() => setIsEditMode((v) => !v)}>
                    {isEditMode ? "Read" : "Edit"}
                </button>

                <button className="btn-save" onClick={handleSave} disabled={!isEditMode}>
                    Save
                </button>

                <button className="btn-delete" onClick={handleDelete}>
                    Delete
                </button>
            </div>

            <MarkdownEditor
                value={content}
                onChange={setContent}
                readOnly={!isEditMode}
                onWikiLink={openWikiLink}
            />
        </div>
    );
}
