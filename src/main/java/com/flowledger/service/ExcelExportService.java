package com.flowledger.service;

import com.flowledger.enums.ExportReport;

public interface ExcelExportService {

    byte[] export(
            ExportReport report
    );

}
