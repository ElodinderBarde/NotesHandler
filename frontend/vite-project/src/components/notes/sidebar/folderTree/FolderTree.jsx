import { useEffect, useState } from "react";
import api from "../../../../utils/api.js";
import { isAuthenticated } from "../../../../utils/authService.js";
import FolderNode from "./FolderNode.jsx";


    export default function FolderTree({ onSelect ,onSelectFolder, onSelectNote , treeData = []}) {

        const [tree, setTree, selectedId, setSelectedId] = useState([]);
        const [selectedFolder, setSelectedFolder] = useState(null);

    async function loadTree() {
        const res = await api.get("/folders/tree");
        setTree(res.data);
    }

    useEffect(() => {
        if (!isAuthenticated()) return;
        loadTree().catch(console.error);
    }, []);

        const handleSelectFolder = (id) => {
            setSelectedFolder(id);
            onSelectFolder(id);
        };

        const handleSelect = (id) => {
            setSelectedId(id);
            onSelect(id);
        };
        return (
            <div className="folder-tree">
                {tree.map(node => (
                    <FolderNode
                        key={node.id}
                        node={node}
                        onSelect={handleSelectFolder}
                        onSelectNote={onSelectNote}
                        selectedId={selectedFolder}
                    />
                ))}
            </div>
        );
    }









