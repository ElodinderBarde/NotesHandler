import { useState } from "react";

export default function FolderNode({ node, level = 0, onSelect, selectedId }) {
    const [open, setOpen] = useState(true);

    const hasChildren = node.children && node.children.length > 0;
    const indent = level * 16; // VS-Code style indentation

    const isSelected = selectedId === node.id;

    return (
        <div className="folder-node">

            {/* HEADER */}
            <div
                className={`folder-header ${isSelected ? "selected" : ""}`}
                style={{ paddingLeft: indent }}
                onClick={() => onSelect(node.id)}
            >
                {/* Toggle icon */}
                {hasChildren && (
                    <span
                        className="toggle"
                        onClick={(e) => {
                            e.stopPropagation();
                            setOpen(!open);
                        }}
                    >
                        {open ? "▼" : "▶"}
                    </span>
                )}

                {/* Folder icon */}
                <span className="folder-icon">
                    {open ? "📂" : "📁"}
                </span>

                {/* Name */}
                <span className="folder-name">{node.name}</span>
            </div>

            {/* CHILDREN */}
            {open && hasChildren && (
                <div>
                    {node.children.map(child => (
                        <FolderNode
                            key={child.id}
                            node={child}
                            level={level + 1}
                            onSelect={onSelect}
                            selectedId={selectedId}
                        />
                    ))}
                </div>
            )}
        </div>
    );
}
