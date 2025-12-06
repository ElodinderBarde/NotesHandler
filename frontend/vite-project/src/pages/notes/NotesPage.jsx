// src/pages/NotesPage.jsx
import Sidebar from "../../components/notes/sidebar/Sidebar.jsx";
import NoteList from "../../components/notes/noteList/NoteList.jsx";
import NoteEditor from "../../components/notes/noteEditor/NoteEditor.jsx";
import { useEffect, useState } from "react";
import api from "../../utils/api.js";
import "./notesPage.css";


export default function NotesPage() {
    const [selectedFolder, setSelectedFolder] = useState(null);
    const [selectedNote, setSelectedNote] = useState(null);
    const [notes, setNotes] = useState([]);

    async function loadNotes() {
        if (!selectedFolder) return;

        const res = await api.get(`/notes/folder/${selectedFolder}`);
        setNotes(res.data);
    }

    useEffect(() => {
        loadNotes();
    }, [selectedFolder]);

    return (
        <div className="notes-container">
            <Sidebar
                selectedFolder={selectedFolder}
                onSelectFolder={setSelectedFolder}
            />

            <NoteList
                notes={notes}
                selectedId={selectedNote}
                onSelect={setSelectedNote}
            />

            <NoteEditor
                note={notes.find(n => n.id === selectedNote)}
                refreshNotes={loadNotes}
            />
        </div>
    );
}
