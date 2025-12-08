import { useState } from "react";

export default function FolderNode({
                                       node,
                                       level = 0,
                                       onSelect,
                                       onSelectNote,
                                       selectedId
                                   }) {
    const [open, setOpen] = useState(true);

    const hasChildren = node.children && node.children.length > 0;
    const hasNotes = node.notes && node.notes.length > 0;

    const indent = level * 16; // VSCode-like indentation
    const isSelected = selectedId === node.id;

    return (
        <div className="folder-node">
            {/* Folder header */}
            <div
                className={`folder-header ${isSelected ? "selected" : ""}`}
                style={{ paddingLeft: indent }}
                onClick={() => onSelect(node.id)}
            >
                {hasChildren || hasNotes ? (
                    <span
                        className="toggle"
                        onClick={(e) => {
                            e.stopPropagation();
                            setOpen(!open);
                        }}
                    >
                        {open ? "▼" : "▶"}
                    </span>
                ) : (
                    <span style={{ width: 16 }}></span>
                )}

                <span className="folder-icon">
                    {open ? "📂" : "📁"}
                </span>

                <span className="folder-name">{node.name}</span>
            </div>

            {/* Render children only if open */}
            {open && (
                <div>
                    {/* Subfolders */}
                    {hasChildren &&
                        node.children.map(child => (
                            <FolderNode
                                key={child.id}
                                node={child}
                                level={level + 1}
                                onSelect={onSelect}
                                onSelectNote={onSelectNote}
                                selectedId={selectedId}
                            />
                        ))}

                    {/* Notes */}
                    {hasNotes &&
                        node.notes.map(note => (
                            <div
                                key={note.id}
                                className="note-item"
                                style={{ paddingLeft: indent + 24 }}
                                onClick={() => onSelectNote(note.id)}
                            >
                                📄 {note.title}
                            </div>
                        ))}
                </div>
            )}
        </div>
    );
}
