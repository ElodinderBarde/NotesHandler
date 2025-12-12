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
    const [selectedNoteId, setSelectedNoteId] = useState(null);


    async function loadNotes(id) {

        if(!id)return;
        const res = await api.get(`/notes/${id}`);
        setSelectedNote(res.data);
    }

    useEffect(() => {

        loadNotes();

        setSelectedNoteId(null);
    }, [selectedFolder]);


    async function openWikiLink(title) {
        try {
            const res = await api.get(`/notes/by-title/${encodeURIComponent(title)}`);
            setSelectedNote(res.data);
        } catch {
            alert(`Note "${title}" nicht gefunden`);
        }
    }

    return (
        <div className="notes-container">


            <Sidebar
                selectedFolder={selectedFolder}
                onSelectFolder={setSelectedFolder}
                onSelectNote={loadNotes}
            />
            <NoteList
                notes={notes}
                selectedId={selectedNote}
                onSelect={setSelectedNote}
            />


            <NoteEditor
                note={selectedNote}
                refreshNotes={() => loadNotes(selectedNote?.id)}
                onWikiLink={openWikiLink}

            />

        </div>
    );
}
