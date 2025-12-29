package com.worker_service.Controllers;
import com.worker_service.serviceImpl.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/workerService/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/generate")
    public ResponseEntity<InputStreamResource> generatePdf() {
        ByteArrayInputStream bis = pdfService.generatePdf("Sample PDF", "This is an example PDF generated in Spring Boot.");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=example.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}