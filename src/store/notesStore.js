const { v4: uuidv4 } = require('uuid');

// In-memory store for notes
const notes = new Map();

/**
 * Get all notes
 * @returns {Array} Array of all notes
 */
function getAllNotes() {
  return Array.from(notes.values()).sort((a, b) => 
    new Date(b.updatedAt) - new Date(a.updatedAt)
  );
}

/**
 * Get a note by ID
 * @param {string} id - Note ID
 * @returns {Object|null} Note object or null if not found
 */
function getNoteById(id) {
  return notes.get(id) || null;
}

/**
 * Create a new note
 * @param {Object} noteData - Note data (title, content)
 * @returns {Object} Created note
 */
function createNote(noteData) {
  const id = uuidv4();
  const now = new Date().toISOString();
  const note = {
    id,
    title: noteData.title || 'Untitled',
    content: noteData.content || '',
    createdAt: now,
    updatedAt: now
  };
  notes.set(id, note);
  return note;
}

/**
 * Update an existing note
 * @param {string} id - Note ID
 * @param {Object} noteData - Updated note data
 * @returns {Object|null} Updated note or null if not found
 */
function updateNote(id, noteData) {
  const note = notes.get(id);
  if (!note) {
    return null;
  }
  const updatedNote = {
    ...note,
    title: noteData.title !== undefined ? noteData.title : note.title,
    content: noteData.content !== undefined ? noteData.content : note.content,
    updatedAt: new Date().toISOString()
  };
  notes.set(id, updatedNote);
  return updatedNote;
}

/**
 * Delete a note
 * @param {string} id - Note ID
 * @returns {boolean} True if deleted, false if not found
 */
function deleteNote(id) {
  return notes.delete(id);
}

/**
 * Search notes by title or content
 * @param {string} query - Search query
 * @returns {Array} Matching notes
 */
function searchNotes(query) {
  const lowerQuery = query.toLowerCase();
  return getAllNotes().filter(note => 
    note.title.toLowerCase().includes(lowerQuery) ||
    note.content.toLowerCase().includes(lowerQuery)
  );
}

/**
 * Clear all notes (for testing)
 */
function clearNotes() {
  notes.clear();
}

module.exports = {
  getAllNotes,
  getNoteById,
  createNote,
  updateNote,
  deleteNote,
  searchNotes,
  clearNotes
};
