const request = require('supertest');
const app = require('../src/server');
const notesStore = require('../src/store/notesStore');

describe('Notes API', () => {
  beforeEach(() => {
    notesStore.clearNotes();
  });

  describe('GET /api/notes', () => {
    it('should return empty array when no notes exist', async () => {
      const res = await request(app).get('/api/notes');
      expect(res.status).toBe(200);
      expect(res.body).toEqual([]);
    });

    it('should return all notes', async () => {
      notesStore.createNote({ title: 'Note 1' });
      notesStore.createNote({ title: 'Note 2' });

      const res = await request(app).get('/api/notes');
      expect(res.status).toBe(200);
      expect(res.body).toHaveLength(2);
    });

    it('should search notes when search query is provided', async () => {
      notesStore.createNote({ title: 'JavaScript' });
      notesStore.createNote({ title: 'Python' });

      const res = await request(app).get('/api/notes?search=JavaScript');
      expect(res.status).toBe(200);
      expect(res.body).toHaveLength(1);
      expect(res.body[0].title).toBe('JavaScript');
    });
  });

  describe('GET /api/notes/:id', () => {
    it('should return a note by ID', async () => {
      const note = notesStore.createNote({ title: 'Test Note' });

      const res = await request(app).get(`/api/notes/${note.id}`);
      expect(res.status).toBe(200);
      expect(res.body.title).toBe('Test Note');
    });

    it('should return 404 for non-existent note', async () => {
      const res = await request(app).get('/api/notes/non-existent');
      expect(res.status).toBe(404);
    });
  });

  describe('POST /api/notes', () => {
    it('should create a new note', async () => {
      const res = await request(app)
        .post('/api/notes')
        .send({ title: 'New Note', content: 'Content' });

      expect(res.status).toBe(201);
      expect(res.body.title).toBe('New Note');
      expect(res.body.content).toBe('Content');
      expect(res.body).toHaveProperty('id');
    });
  });

  describe('PUT /api/notes/:id', () => {
    it('should update an existing note', async () => {
      const note = notesStore.createNote({ title: 'Original' });

      const res = await request(app)
        .put(`/api/notes/${note.id}`)
        .send({ title: 'Updated' });

      expect(res.status).toBe(200);
      expect(res.body.title).toBe('Updated');
    });

    it('should return 404 for non-existent note', async () => {
      const res = await request(app)
        .put('/api/notes/non-existent')
        .send({ title: 'Updated' });

      expect(res.status).toBe(404);
    });
  });

  describe('DELETE /api/notes/:id', () => {
    it('should delete an existing note', async () => {
      const note = notesStore.createNote({ title: 'To Delete' });

      const res = await request(app).delete(`/api/notes/${note.id}`);
      expect(res.status).toBe(204);
    });

    it('should return 404 for non-existent note', async () => {
      const res = await request(app).delete('/api/notes/non-existent');
      expect(res.status).toBe(404);
    });
  });
});
