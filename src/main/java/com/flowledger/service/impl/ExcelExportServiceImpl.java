package com.flowledger.service.impl;

import com.flowledger.dto.response.*;
import com.flowledger.enums.ExportReport;
import com.flowledger.service.DashboardService;
import com.flowledger.service.ExcelExportService;
import com.flowledger.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelExportServiceImpl
        implements ExcelExportService {

    private final DashboardService dashboardService;
    private final TransactionService transactionService;

    @Override
    public byte[] export(ExportReport report) {

        Workbook workbook = new XSSFWorkbook();

        switch (report) {

            case DASHBOARD:
                return exportDashboard(workbook);
            case TRANSACTIONS:
                return exportTransactions(workbook);
            case MONTHLY_SUMMARY:
                return exportMonthlySummary(workbook);

            default:
                throw new IllegalArgumentException(
                        "Unsupported report."
                );
        }

    }

    private byte[] exportDashboard(
            Workbook workbook) {

        DashboardSummaryResponse dashboard =
                dashboardService.getDashboardSummary();

        Sheet sheet =
                workbook.createSheet("Dashboard");

        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);


        Font sectionFont = workbook.createFont();
        sectionFont.setBold(true);

        CellStyle sectionStyle = workbook.createCellStyle();
        sectionStyle.setFont(sectionFont);


        CellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.setDataFormat(
                workbook.createDataFormat()
                        .getFormat("₹#,##0.00")
        );

        Row titleRow = sheet.createRow(0);

        titleRow.createCell(0)
                .setCellValue("FLOWLEDGER DASHBOARD");

        titleRow.getCell(0)
                .setCellStyle(titleStyle);

        Row generatedRow = sheet.createRow(1);

        generatedRow.createCell(0)
                .setCellValue("Generated");

        generatedRow.createCell(1)
                .setCellValue(LocalDate.now().toString());

        Row summaryHeader = sheet.createRow(3);

        summaryHeader.createCell(0)
                .setCellValue("FINANCIAL SUMMARY");

        summaryHeader.getCell(0)
                .setCellStyle(sectionStyle);

        Row incomeRow = sheet.createRow(4);

        incomeRow.createCell(0)
                .setCellValue("Total Income");

        incomeRow.createCell(1)
                .setCellValue(
                        dashboard.getTotalIncome().doubleValue()
                );

        incomeRow.getCell(1)
                .setCellStyle(currencyStyle);


        Row expenseRow = sheet.createRow(5);

        expenseRow.createCell(0)
                .setCellValue("Total Expense");

        expenseRow.createCell(1)
                .setCellValue(
                        dashboard.getTotalExpense().doubleValue()
                );

        expenseRow.getCell(1)
                .setCellStyle(currencyStyle);


        Row balanceRow = sheet.createRow(6);

        balanceRow.createCell(0)
                .setCellValue("Current Balance");

        balanceRow.createCell(1)
                .setCellValue(
                        dashboard.getCurrentBalance().doubleValue()
                );

        balanceRow.getCell(1)
                .setCellStyle(currencyStyle);


        Row transactionRow = sheet.createRow(7);

        transactionRow.createCell(0)
                .setCellValue("Total Transactions");

        transactionRow.createCell(1)
                .setCellValue(
                        dashboard.getTotalTransactions()
                );

        DashboardNotificationsResponse notifications =
                dashboard.getNotifications();

        Row notificationHeader = sheet.createRow(9);

        notificationHeader.createCell(0)
                .setCellValue("NOTIFICATIONS");

        notificationHeader.getCell(0)
                .setCellStyle(sectionStyle);

        Row unreadRow = sheet.createRow(10);

        unreadRow.createCell(0)
                .setCellValue("Unread Notifications");

        unreadRow.createCell(1)
                .setCellValue(
                        notifications.getUnreadCount()
                );

        Row notificationTableHeader = sheet.createRow(12);

        notificationTableHeader.createCell(0)
                .setCellValue("ID");

        notificationTableHeader.createCell(1)
                .setCellValue("Title");

        notificationTableHeader.createCell(2)
                .setCellValue("Due Date");

        notificationTableHeader.createCell(3)
                .setCellValue("Days Remaining");

        notificationTableHeader.createCell(4)
                .setCellValue("Priority");

        List<DashboardNotificationResponse> notificationList =
                notifications.getNotifications();

        int notificationRowIndex = 13;

        for (DashboardNotificationResponse notification
                : notificationList) {

            Row row = sheet.createRow(notificationRowIndex++);

            row.createCell(0)
                    .setCellValue(notification.getId());

            row.createCell(1)
                    .setCellValue(notification.getTitle());

            row.createCell(2)
                    .setCellValue(
                            notification.getDueDate().toString()
                    );

            row.createCell(3)
                    .setCellValue(
                            notification.getDaysRemaining()
                    );

            row.createCell(4)
                    .setCellValue(
                            notification.getPriority().name()
                    );
        }

        DashboardFinancialStreakResponse financialStreaks =
                dashboard.getFinancialStreaks();

        Row streakHeader = sheet.createRow(notificationRowIndex + 2);

        streakHeader.createCell(0)
                .setCellValue("FINANCIAL STREAKS");

        streakHeader.getCell(0)
                .setCellStyle(sectionStyle);

        Row loggingStreakRow = sheet.createRow(
                notificationRowIndex + 3
        );

        loggingStreakRow.createCell(0)
                .setCellValue("Current Logging Streak");

        loggingStreakRow.createCell(1)
                .setCellValue(
                        financialStreaks.getCurrentLoggingStreak()
                );

        Row noSpendStreakRow = sheet.createRow(
                notificationRowIndex + 4
        );

        noSpendStreakRow.createCell(0)
                .setCellValue("Current No-Spend Streak");

        noSpendStreakRow.createCell(1)
                .setCellValue(
                        financialStreaks.getCurrentNoSpendStreak()
                );

        Row motivationRow = sheet.createRow(
                notificationRowIndex + 5
        );

        motivationRow.createCell(0)
                .setCellValue("Motivational Message");

        motivationRow.createCell(1)
                .setCellValue(
                        financialStreaks.getMotivationalMessage()
                );

        sheet.setColumnWidth(0, 28 * 256);
        sheet.setColumnWidth(1, 35 * 256);
        sheet.setColumnWidth(2, 16 * 256);
        sheet.setColumnWidth(3, 18 * 256);
        sheet.setColumnWidth(4, 16 * 256);

        sheet.createFreezePane(0, 3);

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            workbook.write(outputStream);
            workbook.close();

            return outputStream.toByteArray();

        } catch (IOException exception) {

            throw new IllegalStateException(
                    "Failed to generate dashboard Excel export.",
                    exception
            );
        }
    }

    private byte[] exportTransactions(
            Workbook workbook) {

        List<TransactionResponse> transactions =
                transactionService.getMyTransactions();

        Sheet sheet =
                workbook.createSheet("Transactions");

        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);

        CellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.setDataFormat(
                workbook.createDataFormat()
                        .getFormat("₹#,##0.00")
        );

        CellStyle dateTimeStyle = workbook.createCellStyle();
        dateTimeStyle.setDataFormat(
                workbook.createDataFormat()
                        .getFormat("dd-MM-yyyy HH:mm")
        );

        Row titleRow = sheet.createRow(0);

        titleRow.createCell(0)
                .setCellValue("FLOWLEDGER TRANSACTIONS");

        titleRow.getCell(0)
                .setCellStyle(titleStyle);

        Row generatedRow = sheet.createRow(1);

        generatedRow.createCell(0)
                .setCellValue("Generated");

        generatedRow.createCell(1)
                .setCellValue(
                        LocalDate.now().toString()
                );

        Row headerRow = sheet.createRow(3);

        headerRow.createCell(0)
                .setCellValue("ID");

        headerRow.createCell(1)
                .setCellValue("Title");

        headerRow.createCell(2)
                .setCellValue("Amount");

        headerRow.createCell(3)
                .setCellValue("Type");

        headerRow.createCell(4)
                .setCellValue("Category");

        headerRow.createCell(5)
                .setCellValue("Payment Method");

        headerRow.createCell(6)
                .setCellValue("Transaction Timestamp");

        headerRow.createCell(7)
                .setCellValue("Notes");

        for (int i = 0; i < 8; i++) {
            headerRow.getCell(i)
                    .setCellStyle(headerStyle);
        }

        int rowIndex = 4;

        for (TransactionResponse transaction : transactions) {

            Row row = sheet.createRow(rowIndex++);

            row.createCell(0)
                    .setCellValue(transaction.getId());

            row.createCell(1)
                    .setCellValue(transaction.getTitle());

            row.createCell(2)
                    .setCellValue(
                            transaction.getAmount().doubleValue()
                    );

            row.getCell(2)
                    .setCellStyle(currencyStyle);

            row.createCell(3)
                    .setCellValue(
                            transaction.getType().name()
                    );

            row.createCell(4)
                    .setCellValue(
                            transaction.getCategory().name()
                    );

            row.createCell(5)
                    .setCellValue(
                            transaction.getPaymentMethod().name()
                    );

            row.createCell(6)
                    .setCellValue(
                            transaction.getTransactionTimestamp()
                    );

            row.getCell(6)
                    .setCellStyle(dateTimeStyle);

            row.createCell(7)
                    .setCellValue(
                            transaction.getNotes()
                    );
        }

        sheet.setColumnWidth(0, 10 * 256);
        sheet.setColumnWidth(1, 25 * 256);
        sheet.setColumnWidth(2, 18 * 256);
        sheet.setColumnWidth(3, 14 * 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 24 * 256);
        sheet.setColumnWidth(7, 35 * 256);

        sheet.createFreezePane(0, 4);

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            workbook.write(outputStream);
            workbook.close();

            return outputStream.toByteArray();

        } catch (IOException exception) {

            throw new IllegalStateException(
                    "Failed to generate transactions Excel export.",
                    exception
            );
        }
    }

    private byte[] exportMonthlySummary(
            Workbook workbook) {

        List<MonthlySummaryResponse> summaries =
                dashboardService.getMonthlySummary();

        Sheet sheet =
                workbook.createSheet("Monthly Summary");

        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);

        CellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.setDataFormat(
                workbook.createDataFormat()
                        .getFormat("₹#,##0.00")
        );

        CellStyle percentageStyle = workbook.createCellStyle();
        percentageStyle.setDataFormat(
                workbook.createDataFormat()
                        .getFormat("0.00%")
        );

        Row titleRow = sheet.createRow(0);

        titleRow.createCell(0)
                .setCellValue("FLOWLEDGER MONTHLY SUMMARY");

        titleRow.getCell(0)
                .setCellStyle(titleStyle);

        Row generatedRow = sheet.createRow(1);

        generatedRow.createCell(0)
                .setCellValue("Generated");

        generatedRow.createCell(1)
                .setCellValue(
                        LocalDate.now().toString()
                );

        Row headerRow = sheet.createRow(3);

        headerRow.createCell(0)
                .setCellValue("Year");

        headerRow.createCell(1)
                .setCellValue("Month");

        headerRow.createCell(2)
                .setCellValue("Income");

        headerRow.createCell(3)
                .setCellValue("Expense");

        headerRow.createCell(4)
                .setCellValue("Saving");

        headerRow.createCell(5)
                .setCellValue("Saving Rate");

        headerRow.createCell(6)
                .setCellValue("Status");

        for (int i = 0; i < 7; i++) {

            headerRow.getCell(i)
                    .setCellStyle(headerStyle);
        }

        int rowIndex = 4;

        for (MonthlySummaryResponse summary : summaries) {

            Row row = sheet.createRow(rowIndex++);

            row.createCell(0)
                    .setCellValue(
                            summary.getYear()
                    );

            row.createCell(1)
                    .setCellValue(
                            summary.getMonth().name()
                    );

            row.createCell(2)
                    .setCellValue(
                            summary.getIncome().doubleValue()
                    );

            row.getCell(2)
                    .setCellStyle(currencyStyle);

            row.createCell(3)
                    .setCellValue(
                            summary.getExpense().doubleValue()
                    );

            row.getCell(3)
                    .setCellStyle(currencyStyle);

            row.createCell(4)
                    .setCellValue(
                            summary.getSaving().doubleValue()
                    );

            row.getCell(4)
                    .setCellStyle(currencyStyle);

            row.createCell(5)
                    .setCellValue(
                            summary.getSavingRate()
                                    .doubleValue()
                                    / 100
                    );

            row.getCell(5)
                    .setCellStyle(percentageStyle);

            row.createCell(6)
                    .setCellValue(
                            summary.getStatus().name()
                    );
        }

        sheet.setColumnWidth(0, 12 * 256);
        sheet.setColumnWidth(1, 16 * 256);
        sheet.setColumnWidth(2, 18 * 256);
        sheet.setColumnWidth(3, 18 * 256);
        sheet.setColumnWidth(4, 18 * 256);
        sheet.setColumnWidth(5, 18 * 256);
        sheet.setColumnWidth(6, 18 * 256);

        sheet.createFreezePane(0, 4);

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            workbook.write(outputStream);
            workbook.close();

            return outputStream.toByteArray();

        } catch (IOException exception) {

            throw new IllegalStateException(
                    "Failed to generate monthly summary Excel export.",
                    exception
            );
        }
    }

}
