// src/components/notes/noteEditor/MarkdownEditor.jsx
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import rehypeHighlight from "rehype-highlight";

export default function MarkdownEditor({ value, onChange, onWikiLink }) {
    function renderText({ children }) {
        const text = children[0];
        if (typeof text !== "string") return text;

        const parts = text.split(/(\[\[.*?\]\])/g);

        return parts.map((part, i) => {
            const match = part.match(/^\[\[(.+?)\]\]$/);

            if (match) {
                const title = match[1];

                return (
                    <span
                        key={i}
                        className="wiki-link"
                        onClick={() => onWikiLink(title)}
                    >
                        {title}
                    </span>
                );
            }

            return part;
        });
    }





    return (

        <div className="markdown-editor" style={{ display: "flex", gap: "1rem", height: "100%" }}>
            {/* Eingabe */}




            <textarea
                value={value}
                onChange={(e) => onChange(e.target.value)}
                placeholder="Schreibe Markdown…"
                style={{
                    width: "50%",
                    resize: "none",
                    padding: "1rem",
                    fontFamily: "monospace"
                }}
            />


            {/* Vorschau */}
            <div
                style={{
                    width: "50%",
                    padding: "1rem",
                    borderLeft: "1px solid #ccc",
                    overflowY: "auto"
                }}
            >
                <ReactMarkdown

                remarkPlugins={[remarkGfm]}
                    rehypePlugins={[rehypeHighlight]}
                >

                    {value}
                </ReactMarkdown>
            </div>
        </div>
    );
}
