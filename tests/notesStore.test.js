const notesStore = require('../src/store/notesStore');

describe('Notes Store', () => {
  beforeEach(() => {
    notesStore.clearNotes();
  });

  describe('createNote', () => {
    it('should create a new note with title and content', () => {
      const note = notesStore.createNote({
        title: 'Test Note',
        content: 'This is test content'
      });

      expect(note).toHaveProperty('id');
      expect(note.title).toBe('Test Note');
      expect(note.content).toBe('This is test content');
      expect(note).toHaveProperty('createdAt');
      expect(note).toHaveProperty('updatedAt');
    });

    it('should create a note with default title if not provided', () => {
      const note = notesStore.createNote({
        content: 'Content without title'
      });

      expect(note.title).toBe('Untitled');
    });

    it('should create a note with empty content if not provided', () => {
      const note = notesStore.createNote({
        title: 'Title only'
      });

      expect(note.content).toBe('');
    });
  });

  describe('getAllNotes', () => {
    it('should return empty array when no notes exist', () => {
      const notes = notesStore.getAllNotes();
      expect(notes).toEqual([]);
    });

    it('should return all notes sorted by updatedAt descending', async () => {
      notesStore.createNote({ title: 'First' });
      await new Promise(resolve => setTimeout(resolve, 10));
      notesStore.createNote({ title: 'Second' });
      await new Promise(resolve => setTimeout(resolve, 10));
      notesStore.createNote({ title: 'Third' });

      const notes = notesStore.getAllNotes();
      expect(notes).toHaveLength(3);
      expect(notes[0].title).toBe('Third');
    });
  });

  describe('getNoteById', () => {
    it('should return a note by its ID', () => {
      const created = notesStore.createNote({ title: 'Find Me' });
      const found = notesStore.getNoteById(created.id);

      expect(found).toEqual(created);
    });

    it('should return null for non-existent ID', () => {
      const found = notesStore.getNoteById('non-existent-id');
      expect(found).toBeNull();
    });
  });

  describe('updateNote', () => {
    it('should update an existing note', () => {
      const created = notesStore.createNote({ title: 'Original', content: 'Original content' });
      const updated = notesStore.updateNote(created.id, {
        title: 'Updated Title',
        content: 'Updated content'
      });

      expect(updated.title).toBe('Updated Title');
      expect(updated.content).toBe('Updated content');
      expect(updated.id).toBe(created.id);
    });

    it('should return null for non-existent note', () => {
      const result = notesStore.updateNote('non-existent', { title: 'New' });
      expect(result).toBeNull();
    });

    it('should only update provided fields', () => {
      const created = notesStore.createNote({ title: 'Original', content: 'Original content' });
      const updated = notesStore.updateNote(created.id, { title: 'New Title' });

      expect(updated.title).toBe('New Title');
      expect(updated.content).toBe('Original content');
    });
  });

  describe('deleteNote', () => {
    it('should delete an existing note', () => {
      const created = notesStore.createNote({ title: 'To Delete' });
      const result = notesStore.deleteNote(created.id);

      expect(result).toBe(true);
      expect(notesStore.getNoteById(created.id)).toBeNull();
    });

    it('should return false for non-existent note', () => {
      const result = notesStore.deleteNote('non-existent');
      expect(result).toBe(false);
    });
  });

  describe('searchNotes', () => {
    beforeEach(() => {
      notesStore.createNote({ title: 'JavaScript Tutorial', content: 'Learn JS basics' });
      notesStore.createNote({ title: 'Python Guide', content: 'Python programming' });
      notesStore.createNote({ title: 'Web Development', content: 'HTML, CSS, JavaScript' });
    });

    it('should find notes by title', () => {
      const results = notesStore.searchNotes('JavaScript');
      expect(results).toHaveLength(2);
    });

    it('should find notes by content', () => {
      const results = notesStore.searchNotes('Python');
      expect(results).toHaveLength(1);
    });

    it('should be case-insensitive', () => {
      const results = notesStore.searchNotes('javascript');
      expect(results).toHaveLength(2);
    });

    it('should return empty array for no matches', () => {
      const results = notesStore.searchNotes('Ruby');
      expect(results).toHaveLength(0);
    });
  });
});
