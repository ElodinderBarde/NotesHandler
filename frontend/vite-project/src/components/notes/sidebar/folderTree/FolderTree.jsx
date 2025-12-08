import { useEffect, useState } from "react";
import api from "../../../../utils/api.js";
import { isAuthenticated } from "../../../../utils/authService.js";
import FolderNode from "./FolderNode.jsx";


    export default function FolderTree({ onSelect ,onSelectFolder, onSelectNote , treeData = []}) {

        const [tree, setTree] = useState([]);
        const [selectedNoteId, setSelectedNoteId] = useState(null);
        const [selectedFolderId, setSelectedFolderId] = useState(null);

        const [contextMenu, setContextMenu] = useState(null);

    async function loadTree() {
        const res = await api.get("/folders/tree");
        setTree(res.data);
        return res.data;
    }


        useEffect(() => {
            if (!isAuthenticated()) return;

            async function init() {
                const res = await loadTree();

                // Ersten Ordner automatisch auswählen
                if (res?.length > 0) {
                    const first = res[0];
                    setSelectedFolderId(first.id);
                    onSelectFolder?.(first.id);
                }
            }

            init();
        }, []);


        // -------------------------------------------------
        // SELECTION
        // -------------------------------------------------
        function handleFolderSelect(id) {
            setSelectedFolderId(id);
            setSelectedNoteId(null);
        }

        function handleNoteSelect(id) {
            setSelectedNoteId(id);
        }

        // -------------------------------------------------
        // DRAG & DROP (MOVE)
        // -------------------------------------------------
        async function handleMoveFolder(folderId, targetFolderId) {
            if (folderId === targetFolderId) return;

            await fetch(`/api/folders/${folderId}/move`, {
                method: "PATCH",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ targetParentId: targetFolderId })
            });

            await loadTree();
        }

        async function handleMoveNote(noteId, targetFolderId) {
            await fetch(`/api/notes/${noteId}/move`, {
                method: "PATCH",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ targetFolderId })
            });

            await loadTree();
        }

        // -------------------------------------------------
        // CONTEXT MENU
        // -------------------------------------------------
        function openContextMenu(e, type, id) {
            e.preventDefault();
            setContextMenu({
                x: e.clientX,
                y: e.clientY,
                type,
                id
            });
        }

        function closeContextMenu() {
            setContextMenu(null);
        }

        async function handleContextAction(action) {
            if (!contextMenu) return;
            const { type, id } = contextMenu;

            if (action === "new-folder") {
                const name = prompt("Folder name:");
                if (!name) return;
                await fetch("/api/folders", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        name,
                        parentFolderId: type === "folder" ? id : null // bei Note: oberster Root?
                    })
                });
            }

            if (action === "new-note" && type === "folder") {
                const title = prompt("Note title:");
                if (!title) return;
                await fetch("/api/notes", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        title,
                        content: "",
                        folderId: id
                    })
                });
            }

            if (action === "rename") {
                const newName = prompt("New name:");
                if (!newName) return;

                if (type === "folder") {
                    await fetch(`/api/folders/${id}`, {
                        method: "PATCH",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify({ name: newName })
                    });
                } else {
                    await fetch(`/api/notes/${id}`, {
                        method: "PATCH",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify({ title: newName })
                    });
                }
            }

            if (action === "delete") {
                if (!window.confirm("Delete?")) return;

                if (type === "folder") {
                    await fetch(`/api/folders/${id}`, { method: "DELETE" });
                } else {
                    await fetch(`/api/notes/${id}`, { method: "DELETE" });
                }
            }

            closeContextMenu();
            await loadTree();
        }

        return (
            <div className="folder-tree-container">
                <div className="folder-tree-header">Folders</div>

                <div className="folder-tree-scroll">
                    {tree.map(root => (
                        <FolderNode
                            key={root.id}
                            node={root}
                            depth={0}
                            onFolderSelect={handleFolderSelect}
                            onNoteSelect={handleNoteSelect}
                            onMoveFolder={handleMoveFolder}
                            onMoveNote={handleMoveNote}
                            onContextMenu={openContextMenu}
                            selectedFolderId={selectedFolderId}
                            selectedNoteId={selectedNoteId}
                        />
                    ))}
                </div>

                {contextMenu && (
                    <ul
                        className="context-menu"
                        style={{ top: contextMenu.y, left: contextMenu.x }}
                        onMouseLeave={closeContextMenu}
                    >
                        <li onClick={() => handleContextAction("new-folder")}>New Folder</li>
                        {contextMenu.type === "folder" && (
                            <li onClick={() => handleContextAction("new-note")}>New Note</li>
                        )}
                        <li onClick={() => handleContextAction("rename")}>Rename</li>
                        <li onClick={() => handleContextAction("delete")}>Delete</li>
                    </ul>
                )}
            </div>
        );
    }




