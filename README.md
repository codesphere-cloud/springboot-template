# Markdown to PDF Converter

A web application that converts Markdown text to PDF files using Java Spring Boot backend and a simple HTML/JavaScript frontend.

## Features

- Convert Markdown to beautifully formatted PDF documents
- Real-time conversion with user-friendly interface
- Support for common Markdown elements:
  - Headers (H1-H6)
  - Bold and italic text
  - Lists (ordered and unordered)
  - Code blocks and inline code
  - Blockquotes
  - Tables
- Responsive web design
- Download PDF directly from browser

## Technology Stack

### Backend
- **Java 17** with **Spring Boot 3.x**
- **CommonMark Java** for Markdown parsing
- **OpenHTMLtoPDF** for PDF generation
- **Maven** for dependency management

### Frontend
- **HTML5** with modern CSS
- **Vanilla JavaScript** for API interaction
- **Fetch API** for HTTP requests

## Project Structure

```
/
├── backend/
│   ├── pom.xml
│   └── src/main/java/com/example/markdownconverter/
│       ├── MarkdownConverterApplication.java
│       └── PdfController.java
├── frontend/
│   └── index.html
├── ci.yml
└── README.md
```

## API Endpoints

### POST `/api/convert`

Converts Markdown text to PDF.

**Request Body:**
```json
{
  "markdown": "# Your Markdown Content\n\nThis will be converted to PDF."
}
```

**Response:**
- Content-Type: `application/pdf`
- Content-Disposition: `attachment; filename="document.pdf"`
- Body: PDF file bytes

## Running Locally

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Modern web browser

### Backend
```bash
cd backend
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Frontend
```bash
cd frontend
python3 -m http.server 3000
```

The frontend will be available at `http://localhost:3000`

## Running on Codesphere

This project is configured for Codesphere deployment using the `ci.yml` configuration file. Codesphere will automatically:

1. Install dependencies for both frontend and backend
2. Run tests
3. Start both services with proper networking configuration
4. Route frontend requests to `/` and API requests to `/api`

## Usage

1. Open the web application
2. Enter your Markdown content in the textarea
3. Click "Convert to PDF"
4. The PDF will be automatically downloaded

### Keyboard Shortcuts
- `Ctrl + Enter`: Convert to PDF

## Example Markdown

```markdown
# Sample Document

This is a **bold** text and this is *italic*.

## Features
- Convert Markdown to PDF
- Beautiful styling
- Easy to use

```javascript
console.log('Hello, World!');
```

> This is a blockquote example.

### Table Example
| Feature | Status |
|---------|--------|
| Markdown Support | ✅ |
| PDF Generation | ✅ |
| Styling | ✅ |
```

## Dependencies

### Backend Dependencies
- `spring-boot-starter-web`: Web framework
- `commonmark`: Markdown parsing
- `openhtmltopdf-pdfbox`: HTML to PDF conversion

### Frontend Dependencies
- No external dependencies - uses vanilla JavaScript

## License

This project is a template for Codesphere applications.
