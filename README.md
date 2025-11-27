# NotesHandler

A miniature Wiki-like online application that can handle your notes from everywhere.

## Features

- 📝 Create, read, update, and delete notes
- 🔍 Search notes by title or content
- 💾 In-memory storage (notes persist during server runtime)
- 🎨 Clean, responsive UI
- ⚡ Fast and lightweight

## Getting Started

### Prerequisites

- Node.js (v16 or higher)
- npm

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/ElodinderBarde/NotesHandler.git
   cd NotesHandler
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the server:
   ```bash
   npm start
   ```

4. Open your browser and navigate to `http://localhost:3000`

### Development

Run in development mode:
```bash
npm run dev
```

### Testing

Run the test suite:
```bash
npm test
```

### Linting

Run ESLint:
```bash
npm run lint
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/notes` | Get all notes |
| GET | `/api/notes?search=query` | Search notes |
| GET | `/api/notes/:id` | Get a note by ID |
| POST | `/api/notes` | Create a new note |
| PUT | `/api/notes/:id` | Update a note |
| DELETE | `/api/notes/:id` | Delete a note |

## Project Structure

```
NotesHandler/
├── public/           # Frontend files
│   ├── index.html    # Main HTML page
│   ├── styles.css    # Styles
│   └── app.js        # Frontend JavaScript
├── src/              # Backend source code
│   ├── server.js     # Express server
│   ├── routes/       # API routes
│   │   └── notes.js  # Notes routes
│   └── store/        # Data store
│       └── notesStore.js
├── tests/            # Test files
├── package.json      # Project dependencies
└── README.md         # This file
```

## License

MIT
