// src/components/notes/NoteEditor.test.jsx
import { render, screen } from "@testing-library/react";
import NoteEditor from "../components/notes/noteEditor/NoteEditor";

describe("NoteEditor", () => {
    test("zeigt Hinweis wenn keine Note ausgewählt ist", () => {
        render(
            <NoteEditor
                note={null}
                refreshNotes={() => {}}
            />
        );

        expect(
            screen.getByText(/select a note/i)
        ).toBeInTheDocument();
    });

    test("Editor ist standardmässig im Read-Only-Modus", () => {
        render(
            <NoteEditor
                note={{ id: 1, title: "Test", content: "Hallo" }}
                refreshNotes={() => {}}
            />
        );

        const textarea = screen.getByRole("textbox");
        expect(textarea).toBeDisabled();
    });
});
