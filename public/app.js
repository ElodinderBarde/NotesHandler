// API base URL
const API_URL = '/api/notes';

// State
let currentNoteId = null;

// DOM Elements
const notesList = document.getElementById('notes-list');
const searchInput = document.getElementById('search-input');
const newNoteBtn = document.getElementById('new-note-btn');
const noteEditor = document.getElementById('note-editor');
const noNoteSelected = document.getElementById('no-note-selected');
const noteTitleInput = document.getElementById('note-title');
const noteContentInput = document.getElementById('note-content');
const saveNoteBtn = document.getElementById('save-note-btn');
const deleteNoteBtn = document.getElementById('delete-note-btn');

// Fetch all notes
async function fetchNotes(searchQuery = '') {
  try {
    const url = searchQuery ? `${API_URL}?search=${encodeURIComponent(searchQuery)}` : API_URL;
    const response = await fetch(url);
    if (!response.ok) throw new Error('Failed to fetch notes');
    return await response.json();
  } catch (error) {
    console.error('Error fetching notes:', error);
    return [];
  }
}

// Fetch a single note
async function fetchNote(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`);
    if (!response.ok) throw new Error('Failed to fetch note');
    return await response.json();
  } catch (error) {
    console.error('Error fetching note:', error);
    return null;
  }
}

// Create a new note
async function createNote(noteData) {
  try {
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(noteData)
    });
    if (!response.ok) throw new Error('Failed to create note');
    return await response.json();
  } catch (error) {
    console.error('Error creating note:', error);
    return null;
  }
}

// Update an existing note
async function updateNote(id, noteData) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(noteData)
    });
    if (!response.ok) throw new Error('Failed to update note');
    return await response.json();
  } catch (error) {
    console.error('Error updating note:', error);
    return null;
  }
}

// Delete a note
async function deleteNote(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: 'DELETE'
    });
    return response.ok;
  } catch (error) {
    console.error('Error deleting note:', error);
    return false;
  }
}

// Format date for display
function formatDate(dateString) {
  const date = new Date(dateString);
  return date.toLocaleDateString('en-US', {
    month: 'short',
    day: 'numeric',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  });
}

// Render notes list
async function renderNotesList(searchQuery = '') {
  const notes = await fetchNotes(searchQuery);
  notesList.innerHTML = '';

  if (notes.length === 0) {
    notesList.innerHTML = '<li class="no-notes" style="padding: 1rem; color: #999; text-align: center;">No notes yet. Create your first note!</li>';
    return;
  }

  notes.forEach(note => {
    const li = document.createElement('li');
    li.className = `note-item${note.id === currentNoteId ? ' active' : ''}`;
    li.dataset.id = note.id;
    
    const preview = note.content.substring(0, 50) + (note.content.length > 50 ? '...' : '');
    
    li.innerHTML = `
      <div class="note-item-title">${escapeHtml(note.title) || 'Untitled'}</div>
      <div class="note-item-preview">${escapeHtml(preview) || 'No content'}</div>
      <div class="note-item-date">${formatDate(note.updatedAt)}</div>
    `;
    
    li.addEventListener('click', () => selectNote(note.id));
    notesList.appendChild(li);
  });
}

// Escape HTML to prevent XSS
function escapeHtml(text) {
  const div = document.createElement('div');
  div.textContent = text;
  return div.innerHTML;
}

// Select a note for editing
async function selectNote(id) {
  currentNoteId = id;
  const note = await fetchNote(id);
  
  if (note) {
    noteTitleInput.value = note.title;
    noteContentInput.value = note.content;
    noteEditor.classList.remove('hidden');
    noNoteSelected.style.display = 'none';
    renderNotesList(searchInput.value);
  }
}

// Show editor for new note
function showNewNoteEditor() {
  currentNoteId = null;
  noteTitleInput.value = '';
  noteContentInput.value = '';
  noteEditor.classList.remove('hidden');
  noNoteSelected.style.display = 'none';
  noteTitleInput.focus();
  
  // Remove active class from all notes
  document.querySelectorAll('.note-item').forEach(item => item.classList.remove('active'));
}

// Save the current note
async function saveCurrentNote() {
  const title = noteTitleInput.value.trim();
  const content = noteContentInput.value.trim();

  if (!title && !content) {
    alert('Please enter a title or content for your note.');
    return;
  }

  let note;
  if (currentNoteId) {
    note = await updateNote(currentNoteId, { title, content });
  } else {
    note = await createNote({ title, content });
    if (note) {
      currentNoteId = note.id;
    }
  }

  if (note) {
    renderNotesList(searchInput.value);
  }
}

// Delete the current note
async function deleteCurrentNote() {
  if (!currentNoteId) return;

  const confirmed = confirm('Are you sure you want to delete this note?');
  if (!confirmed) return;

  const deleted = await deleteNote(currentNoteId);
  if (deleted) {
    currentNoteId = null;
    noteEditor.classList.add('hidden');
    noNoteSelected.style.display = 'flex';
    renderNotesList(searchInput.value);
  }
}

// Debounce function for search
function debounce(func, wait) {
  let timeout;
  return function executedFunction(...args) {
    const later = () => {
      clearTimeout(timeout);
      func(...args);
    };
    clearTimeout(timeout);
    timeout = setTimeout(later, wait);
  };
}

// Event Listeners
newNoteBtn.addEventListener('click', showNewNoteEditor);
saveNoteBtn.addEventListener('click', saveCurrentNote);
deleteNoteBtn.addEventListener('click', deleteCurrentNote);

searchInput.addEventListener('input', debounce((e) => {
  renderNotesList(e.target.value);
}, 300));

// Initialize the app
document.addEventListener('DOMContentLoaded', () => {
  renderNotesList();
});
