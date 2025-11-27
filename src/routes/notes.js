const express = require('express');
const router = express.Router();
const notesStore = require('../store/notesStore');

// GET /api/notes - Get all notes
router.get('/', (req, res) => {
  const { search } = req.query;
  if (search) {
    const notes = notesStore.searchNotes(search);
    return res.json(notes);
  }
  const notes = notesStore.getAllNotes();
  res.json(notes);
});

// GET /api/notes/:id - Get a note by ID
router.get('/:id', (req, res) => {
  const note = notesStore.getNoteById(req.params.id);
  if (!note) {
    return res.status(404).json({ error: 'Note not found' });
  }
  res.json(note);
});

// POST /api/notes - Create a new note
router.post('/', (req, res) => {
  const { title, content } = req.body;
  const note = notesStore.createNote({ title, content });
  res.status(201).json(note);
});

// PUT /api/notes/:id - Update a note
router.put('/:id', (req, res) => {
  const { title, content } = req.body;
  const note = notesStore.updateNote(req.params.id, { title, content });
  if (!note) {
    return res.status(404).json({ error: 'Note not found' });
  }
  res.json(note);
});

// DELETE /api/notes/:id - Delete a note
router.delete('/:id', (req, res) => {
  const deleted = notesStore.deleteNote(req.params.id);
  if (!deleted) {
    return res.status(404).json({ error: 'Note not found' });
  }
  res.status(204).send();
});

module.exports = router;
