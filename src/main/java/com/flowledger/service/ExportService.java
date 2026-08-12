package com.flowledger.service;

import com.flowledger.enums.ExportFormat;
import com.flowledger.enums.ExportReport;

public interface ExportService {

    byte[] export(
            ExportReport report,
            ExportFormat format
    );

}
