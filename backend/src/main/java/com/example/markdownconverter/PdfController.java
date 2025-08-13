package com.example.markdownconverter;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PdfController {

    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();

    @PostMapping("/convert")
    public ResponseEntity<byte[]> convertMarkdownToPdf(@RequestBody MarkdownRequest request) {
        try {
            // Parse markdown to HTML
            Node document = parser.parse(request.markdown());
            String html = renderer.render(document);
            
            // Wrap HTML in a complete document with basic styling
            String completeHtml = createCompleteHtml(html);
            
            // Convert HTML to PDF
            byte[] pdfBytes = convertHtmlToPdf(completeHtml);
            
            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "document.pdf");
            headers.setContentLength(pdfBytes.length);
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error converting markdown to PDF: " + e.getMessage()).getBytes());
        }
    }

    private String createCompleteHtml(String bodyContent) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8"/>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            line-height: 1.6;
                            margin: 40px;
                            color: #333;
                        }
                        h1, h2, h3, h4, h5, h6 {
                            color: #2c3e50;
                            margin-top: 30px;
                            margin-bottom: 15px;
                        }
                        p {
                            margin-bottom: 15px;
                        }
                        code {
                            background-color: #f4f4f4;
                            padding: 2px 5px;
                            border-radius: 3px;
                            font-family: 'Courier New', monospace;
                        }
                        pre {
                            background-color: #f4f4f4;
                            padding: 15px;
                            border-radius: 5px;
                            overflow-x: auto;
                        }
                        blockquote {
                            border-left: 4px solid #ddd;
                            margin: 0;
                            padding-left: 20px;
                            font-style: italic;
                        }
                        ul, ol {
                            margin-bottom: 15px;
                        }
                        li {
                            margin-bottom: 5px;
                        }
                    </style>
                </head>
                <body>
                """ + bodyContent + """
                </body>
                </html>
                """;
    }

    private byte[] convertHtmlToPdf(String html) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();
            return outputStream.toByteArray();
        }
    }

    public record MarkdownRequest(String markdown) {}
}
