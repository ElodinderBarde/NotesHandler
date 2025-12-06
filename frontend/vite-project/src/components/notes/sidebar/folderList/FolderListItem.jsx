// src/components/sidebar/FolderListItem.jsx
export default function FolderListItem({ folder, selected, onSelect }) {
    return (
        <div
            className={`folder-item ${selected ? "selected" : ""}`}
            onClick={onSelect}
        >
            <span className="folder-icon">📁</span>
            <span className="folder-name">{folder.name}</span>
        </div>
    );
}
