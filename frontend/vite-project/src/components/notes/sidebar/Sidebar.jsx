import "./sidebar.css";
import FolderTree from "./folderTree/FolderTree.jsx";

export default function Sidebar({ onFolderSelect }) {

    return (
        <div className="sidebar">

            <div className="sidebar-header">
                <h3>Folders</h3>
            </div>

            <div className="folder-list">
                <FolderTree onSelect={onFolderSelect} />
            </div>

        </div>
    );
}
