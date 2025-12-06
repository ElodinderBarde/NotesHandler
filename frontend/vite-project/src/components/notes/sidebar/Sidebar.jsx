// src/components/sidebar/Sidebar.jsx
import { useEffect, useState } from "react";
import FolderList from "../noteList/NoteList.jsx";
import api from "../../../utils/api";
import "./sidebar.css";

export default function Sidebar({ selectedFolder, onSelectFolder }) {
    const [folders, setFolders] = useState([]);

    async function loadFolders() {
        const res = await api.get("/folders");
        setFolders(res.data);
    }

    async function handleCreateFolder() {
        const name = prompt("Folder name:");
        if (!name) return;

        await api.post("/folders", { name });
        loadFolders();
    }

    useEffect(() => {
        loadFolders();
    }, []);

    return (
        <div className="sidebar">
            <div className="sidebar-header">
                <h3>Folders</h3>
                <button className="btn-add-folder" onClick={handleCreateFolder}>
                    + Folder
                </button>
            </div>

            <FolderList
                folders={folders}
                selectedFolder={selectedFolder}
                onSelectFolder={onSelectFolder}
            />
        </div>
    );
}
