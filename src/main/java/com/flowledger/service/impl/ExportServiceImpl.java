package com.flowledger.service.impl;

import com.flowledger.enums.ExportFormat;
import com.flowledger.enums.ExportReport;
import com.flowledger.service.ExcelExportService;
import com.flowledger.service.ExportService;
import com.flowledger.service.PdfExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {

    private final ExcelExportService excelExportService;

    private final PdfExportService pdfExportService;

    @Override
    public byte[] export(
            ExportReport report,
            ExportFormat format) {

        switch (format) {

            case EXCEL:
                return excelExportService.export(report);

            case PDF:
                return pdfExportService.export(report);

            default:
                throw new IllegalArgumentException(
                        "Unsupported export format."
                );

        }
    }


}
