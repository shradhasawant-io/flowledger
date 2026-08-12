package com.flowledger.service;

import com.flowledger.enums.ExportReport;

public interface PdfExportService {

    byte[] export(
            ExportReport report
    );

}
