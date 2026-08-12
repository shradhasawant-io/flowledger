package com.flowledger.controller;

import com.flowledger.enums.ExportFormat;
import com.flowledger.enums.ExportReport;
import com.flowledger.service.ExportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/export")
@SecurityRequirement(name = "bearerAuth")
public class ExportController {

    private final ExportService exportService;

    @GetMapping
    public ResponseEntity<byte[]> export(
            @RequestParam ExportReport report,
            @RequestParam ExportFormat format) {

        byte[] file =
                exportService.export(report, format);

        String extension =
                format == ExportFormat.EXCEL
                        ? "xlsx"
                        : "pdf";

        String filename =
                "flowledger-"
                        + report.name().toLowerCase()
                        + "."
                        + extension;

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                format == ExportFormat.EXCEL
                        ? MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                )
                        : MediaType.APPLICATION_PDF
        );

        headers.setContentDisposition(
                ContentDisposition
                        .attachment()
                        .filename(filename)
                        .build()
        );

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(file);
    }
}