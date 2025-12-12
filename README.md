# NotesHandler

**Projektdokumentation – Modul 223**

**Autor:** Roberto Marchese
**Datum:** 12.12.2025
**Version:** 1.0

---

## Einleitung

NotesHandler ist eine webbasierte Notizverwaltung, die es Benutzer:innen ermöglicht, strukturierte Notizen in Ordnern zu organisieren und Inhalte mithilfe von Markdown zu verfassen.
Das Projekt wurde im Rahmen des Moduls 223 entwickelt und legt den Fokus auf eine saubere Backend-Architektur, sichere Authentifizierung sowie eine klare Trennung zwischen Frontend und Backend.

---

## Projektidee

Ziel des Projekts ist die Entwicklung einer persönlichen, mehrbenutzerfähigen Notiz-Applikation mit folgenden Kernfunktionen:

* Benutzer-Authentifizierung (Login / Registrierung)
* Verwaltung von Notizen und Ordnern
* Markdown-Unterstützung für strukturierte Inhalte
* Klare REST-API zur Anbindung eines React-Frontends
* Erweiterbarkeit (z. B. Wiki-Links, Kategorien, Versionierung)(Aus Zeitgründen nicht abschliessend umgesetzt)

---

## Anforderungsanalyse

### Kernanforderungen

**Backend**

* Benutzerverwaltung mit JWT-basierter Authentifizierung
* CRUD-Funktionalität für Notizen
* Ordnerstruktur (hierarchisch)
* Zugriffsschutz pro Benutzer:in
* REST-konforme Endpunkte

**Frontend**

* Übersichtliche Darstellung von Ordnern und Notizen
* Editor mit Markdown-Vorschau
* Read-/Edit-Modus für Notizen
* Kommunikation ausschliesslich über REST-API

---

## User Stories

* **User Story 1:**
  Als Benutzer:in möchte ich mich registrieren und einloggen, damit meine Notizen geschützt sind.

* **User Story 2:**
  Als Benutzer:in möchte ich Notizen erstellen, bearbeiten, löschen und Ordnern zuweisen können.

* **User Story 3:**
  Als Benutzer:in möchte ich Markdown verwenden, um strukturierte und lesbare Inhalte zu erstellen.

---

## Backend-Architektur

### Layered Architecture

Das Backend folgt einer klassischen **Layered Architecture**, um Verantwortlichkeiten klar zu trennen und Wartbarkeit zu gewährleisten.

**Schichten:**

* **Controller Layer**
  Verarbeitet HTTP-Requests und stellt REST-Endpunkte bereit.

* **Service Layer**
  Enthält die Business-Logik (z. B. Zugriffskontrolle, Validierung, Notenverwaltung).

* **Repository Layer**
  Kapselt den Datenbankzugriff über Spring Data JPA.

Das React-Frontend kommuniziert ausschliesslich über die REST-API mit dem Backend.


---

## Datenbankmodell (ER-Diagramm)

Die Datenbank basiert auf einer relationalen Struktur in PostgreSQL.

Zentrale Entitäten:

* `app_users`
* `worldnotes_note`
* `worldnotes_folder`
* `worldnotes_note_link` (optional)
* `worldnotes_category` (optional)

Jede Notiz gehört genau einem Benutzer.
Ordner können hierarchisch aufgebaut sein.
Fremdschlüssel stellen die referenzielle Integrität sicher.


---

## Authentifizierung (JWT)

Die Authentifizierung erfolgt über **JSON Web Tokens (JWT)**:

1. Login mit Benutzername & Passwort
2. Backend generiert JWT
3. Token wird im `Authorization`-Header (`Bearer <token>`) mitgesendet
4. Token wird serverseitig validiert
5. Zugriff erfolgt kontextabhängig auf den eingeloggten Benutzer

---

## Technologie-Stack (Backend)

| Technologie                 | Version | Verwendung              |
| --------------------------- | ------- | ----------------------- |
| Java                        | 21      | Programmiersprache      |
| Spring Boot                 | 3.3.7   | Backend-Framework       |
| Spring Web                  | 3.3.7   | REST-API                |
| Spring Security             | 6.x     | Authentifizierung       |
| JWT (JJWT)                  | 0.11.5  | Token-basierte Security |
| BCrypt                      | –       | Passwort-Hashing        |
| Spring Data JPA / Hibernate | 6.x     | ORM                     |
| PostgreSQL                  | 16      | Produktivdatenbank      |
| H2                          | –       | Testdatenbank           |
| Maven                       | 3.x     | Build-Tool              |
| Springdoc OpenAPI           | 2.3.0   | API-Dokumentation       |
| Spring Boot Test            | 3.3.7   | Tests                   |
| Spring Security Test        | 6.x     | Security-Tests          |

---

## Frontend

Das Frontend wird mit **React** umgesetzt und kommuniziert ausschliesslich über die REST-API mit dem Backend.

### Aktuelle Funktionen

* Ordner- und Notizenübersicht
* Drag-and-Drop für Ordner/Notizen
* Context-Menü auf Ordnern/Notizen
* Markdown-Editor mit Preview
* Read-Only-Modus (Standard)
* Edit-Modus per Button
* Speicherung über REST


---

## Sicherheitskonzept

* Passwort-Hashing mit BCrypt
* JWT-basierte Authentifizierung
* Zugriff auf Notizen ausschliesslich benutzergebunden, mit serverseitiger Validierung
* Keine sensiblen Daten im Klartext gespeichert

---

## Testplan

**Backend**

* Unit-Tests für Services
* Security-Tests für geschützte Endpunkte

**Frontend**

* Manuelle Tests der Kernfunktionen 

---

## Installationsanleitung

Folgend ist eine Schritt-für-Schritt-Anleitung zur manuellen Installation und zum Starten der Anwendung.
Das Docker Compose Setup erlaubt jedoch eine inbetriebnahme mit IntelliJ durch starten des Debuggers, der die Backend- und Frontend-Module nacheinander startet, sowie die Datenbank mit 
Docker Compose hochfährt und mit Initialdaten befüllt.

Die Passwörter für die initialen Benutzer sind in den .env-Dateien im Backend-Docker-Ordner hinterlegt.
Fügen Sie diese bitte entsprechend Ihrer lokalen Umgebung in die IntelliJ Run/Debug Konfigurationen ein.

Darunter: 
- Passwort für den Datenbankbenutzer
- Benutzername
- JWT Secret Key für die Token-Generierung


Abhängig ob Sie mit ```npm run dev``` oder mit dem Backend-Container arbeiten möchten, erreichen Sie das Frontend unter:

- http://localhost:5173 (npm run dev)
- http://localhost:8084 (Frontend-Container)


### Voraussetzungen

**Backend**

* Java JDK 21
* Maven
* Docker & Docker Compose
* Git

**Frontend**

* Node.js (LTS)
* npm

**Empfohlene IDEs**

* Backend: IntelliJ IDEA
* Frontend: Visual Studio Code

---

### Projekt klonen

```bash
git clone https://github.com/ElodinderBarde/NotesHandler
cd NotesHandler
```

---

### Backend – Datenbank starten

```bash
cd backend/docker
docker-compose up -d
```

**Datenbank**

* Host: localhost
* Port: 5432
* Datenbank: noteshandler
* Benutzer / Passwort: siehe `docker-compose.yml`

---

### Backend starten

```bash
mvn spring-boot:run
```

Backend erreichbar unter:
 `http://localhost:8080`

---

## Dokumentation

* **Swagger / OpenAPI:** `http://localhost:8080/swagger-ui.html`
* **JavaDoc:** tbd

---

## Hilfsmittel

### ChatGPT

Wurde punktuell zur Unterstützung eingesetzt, insbesondere für:

* Architektur-Reviews
* Fehleranalyse
* Refactoring-Hinweise
* Dokumentationsstruktur

### SideQuests Modul 223

* Strukturvorgaben
* Architekturbeispiele
* Technische Orientierung

---

## Anmerkungen

* Einige Diagramme werden bewusst extern verlinkt, um die README übersichtlich zu halten.
* Erweiterungen wie Wiki-Links oder Versionierung sind konzeptionell vorbereitet.

---

