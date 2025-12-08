import { useEffect, useState } from "react";
import api from "../../../../utils/api.js";
import { isAuthenticated } from "../../../../utils/authService.js";
import FolderNode from "./FolderNode.jsx";


    export default function FolderTree({ onSelect , treeData = []}) {

        const [tree, setTree, selectedId, setSelectedId] = useState([]);

    async function loadTree() {
        const res = await api.get("/folders/tree");
        setTree(res.data);
    }

    useEffect(() => {
        if (!isAuthenticated()) return;
        loadTree().catch(console.error);
    }, []);


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
                        onSelect={handleSelect}
                        selectedId={selectedId}
                    />
                ))}
            </div>
        );
    }









