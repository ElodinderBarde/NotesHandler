// src/components/sidebar/FolderList.jsx
import FolderListItem from "./FolderListItem.jsx";

export default function FolderList({ folders, selectedFolder, onSelectFolder }) {
    return (
        <div className="folder-list">
            {folders.length === 0 && (
                <div className="empty-folders">No folders yet</div>
            )}

            {folders.map(folder => (
                <FolderListItem
                    key={folder.id}
                    folder={folder}
                    selected={folder.id === selectedFolder}
                    onSelect={() => onSelectFolder(folder.id)}
                />
            ))}
        </div>
    );
}