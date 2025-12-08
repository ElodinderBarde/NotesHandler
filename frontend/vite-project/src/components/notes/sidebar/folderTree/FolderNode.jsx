import { useState } from "react";

export default function FolderNode({
                                       node,
                                       depth,
                                       onFolderSelect,
                                       onNoteSelect,
                                       onMoveFolder,
                                       onMoveNote,
                                       onContextMenu,
                                       selectedFolderId,
                                       selectedNoteId
                                   }) {
    const [open, setOpen] = useState(true);

    const hasChildren = node.children && node.children.length > 0;
    const hasNotes = node.notes && node.notes.length > 0;

    const indentStyle = { paddingLeft: depth * 14 + 8 };

    // ----------------- DRAG HANDLER -----------------
    function handleFolderDragStart(e) {
        e.dataTransfer.setData("type", "folder");
        e.dataTransfer.setData("id", String(node.id));
    }

    function handleFolderDragOver(e) {
        e.preventDefault(); // Drop erlauben
    }

    function handleFolderDrop(e) {
        e.preventDefault();
        const type = e.dataTransfer.getData("type");
        const id = Number(e.dataTransfer.getData("id"));

        if (type === "folder") {
            onMoveFolder(id, node.id);
        } else if (type === "note") {
            onMoveNote(id, node.id);
        }
    }

    return (
        <div className="folder-node">
            {/* FOLDER-HEADER */}
            <div
                className={
                    "folder-header" +
                    (selectedFolderId === node.id ? " is-selected" : "")
                }
                style={indentStyle}
                onClick={() => onFolderSelect(node.id)}
                onContextMenu={(e) => onContextMenu(e, "folder", node.id)}
                draggable
                onDragStart={handleFolderDragStart}
                onDragOver={handleFolderDragOver}
                onDrop={handleFolderDrop}
            >
                {/* Pfeil / Toggle */}
                <span
                    className="folder-toggle"
                    onClick={(e) => {
                        e.stopPropagation();
                        setOpen(!open);
                    }}
                >
                    {hasChildren || hasNotes ? (open ? "▼" : "▶") : "•"}
                </span>

                {/* Icon + Name */}
                <span className="folder-icon">📁</span>
                <span className="folder-name">{node.name}</span>
            </div>

            {/* CHILDREN + NOTES */}
            {open && (
                <div className="folder-children" id={"folder"+node.id}>

                    {/* NOTES ALS „FILES“ */}
                    {hasNotes && node.notes.map(note => (
                        <div
                            key={note.id}
                            className={
                                "note-row" +
                                (selectedNoteId === note.id ? " is-selected" : "")
                            }
                            style={{ paddingLeft: depth * 14 + 28 }}
                            onClick={() => onNoteSelect(note.id)}
                            onContextMenu={(e) => onContextMenu(e, "note", note.id)}
                            draggable
                            onDragStart={(e) => {
                                e.dataTransfer.setData("type", "note");
                                e.dataTransfer.setData("id", String(note.id));
                            }}
                        >
                            <span className="note-icon">📄</span>
                            <span className="note-title">{note.title}</span>
                        </div>
                    ))}

                    {/* UNTERORDNER */}
                    {hasChildren && node.children.map(child => (
                        <FolderNode
                            key={child.id}
                            node={child}
                            depth={depth + 1}
                            onFolderSelect={onFolderSelect}
                            onNoteSelect={onNoteSelect}
                            onMoveFolder={onMoveFolder}
                            onMoveNote={onMoveNote}
                            onContextMenu={onContextMenu}
                            selectedFolderId={selectedFolderId}
                            selectedNoteId={selectedNoteId}
                        />
                    ))}
                </div>
            )}
        </div>
    );
}
