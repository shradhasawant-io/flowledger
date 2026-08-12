package com.flowledger.service.impl;

import com.flowledger.dto.response.DashboardNotificationResponse;
import com.flowledger.dto.response.DashboardSummaryResponse;
import com.flowledger.dto.response.TransactionResponse;
import com.flowledger.service.DashboardService;
import com.flowledger.service.PdfExportService;
import com.flowledger.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.openpdf.text.Document;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import com.flowledger.enums.ExportReport;
import java.time.LocalDate;
import java.io.ByteArrayOutputStream;
import java.util.List;
import com.flowledger.dto.response.MonthlySummaryResponse;
import org.openpdf.text.PageSize;
import org.openpdf.text.Font;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfPCell;

@Service
@RequiredArgsConstructor
public class PdfExportServiceImpl implements PdfExportService {

    private final DashboardService dashboardService;
    private final TransactionService transactionService;

    @Override
    public byte[] export(ExportReport report) {

        switch (report) {

            case DASHBOARD:
                return exportDashboard();

            case TRANSACTIONS:
                return exportTransactions();

            case MONTHLY_SUMMARY:
                return exportMonthlySummary();

            default:
                throw new IllegalArgumentException(
                        "Unsupported report."
                );
        }
    }

    private byte[] exportDashboard() {

        DashboardSummaryResponse dashboard =
                dashboardService.getDashboardSummary();

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            Document document = new Document(
                    PageSize.A4,
                    36,
                    36,
                    36,
                    36
            );

            PdfWriter.getInstance(
                    document,
                    outputStream
            );

            document.open();

            Font titleFont = new Font(
                    Font.HELVETICA,
                    18,
                    Font.BOLD
            );

            Font sectionFont = new Font(
                    Font.HELVETICA,
                    13,
                    Font.BOLD
            );

            Font headerFont = new Font(
                    Font.HELVETICA,
                    10,
                    Font.BOLD
            );

            Font normalFont = new Font(
                    Font.HELVETICA,
                    10,
                    Font.NORMAL
            );

            document.add(
                    new Paragraph(
                            "FLOWLEDGER DASHBOARD",
                            titleFont
                    )
            );

            document.add(
                    new Paragraph(
                            "Generated: "
                                    + LocalDate.now(),
                            normalFont
                    )
            );

            document.add(
                    new Paragraph(
                            "FINANCIAL SUMMARY",
                            sectionFont
                    )
            );

            document.add(
                    new Paragraph(" ")
            );

            PdfPTable table = new PdfPTable(2);

            table.setWidthPercentage(100);

            table.addCell(
                    new PdfPCell(
                            new Paragraph("Metric", headerFont)
                    )
            );

            table.addCell(
                    new PdfPCell(
                            new Paragraph("Value", headerFont)
                    )
            );

            table.setHeaderRows(1);

            table.addCell("Total Income");

            table.addCell(
                    "₹" + dashboard.getTotalIncome()
            );

            table.addCell("Total Expense");

            table.addCell(
                    "₹" + dashboard.getTotalExpense()
            );

            table.addCell("Current Balance");

            table.addCell(
                    "₹" + dashboard.getCurrentBalance()
            );

            table.addCell("Total Transactions");

            table.addCell(
                    dashboard.getTotalTransactions().toString()
            );

            document.add(table);

            document.add(
                    new Paragraph(" ")
            );

            document.add(
                    new Paragraph(
                            "NOTIFICATIONS",
                            sectionFont
                    )
            );

            document.add(
                    new Paragraph(" ")
            );

            document.add(
                    new Paragraph(
                            "Unread Notifications: "
                                    + dashboard.getNotifications()
                                    .getUnreadCount()
                    )
            );

            PdfPTable notificationTable =
                    new PdfPTable(5);

            notificationTable.setWidthPercentage(100);

            notificationTable.addCell(
                    new PdfPCell(
                            new Paragraph("ID", headerFont)
                    )
            );

            notificationTable.addCell(
                    new PdfPCell(
                            new Paragraph("Title", headerFont)
                    )
            );

            notificationTable.addCell(
                    new PdfPCell(
                            new Paragraph("Due Date", headerFont)
                    )
            );

            notificationTable.addCell(
                    new PdfPCell(
                            new Paragraph("Days Remaining", headerFont)
                    )
            );

            notificationTable.addCell(
                    new PdfPCell(
                            new Paragraph("Priority", headerFont)
                    )
            );

            notificationTable.setHeaderRows(1);

            for (DashboardNotificationResponse notification
                    : dashboard.getNotifications().getNotifications()) {

                notificationTable.addCell(
                        notification.getId().toString()
                );

                notificationTable.addCell(
                        notification.getTitle()
                );

                notificationTable.addCell(
                        notification.getDueDate().toString()
                );

                notificationTable.addCell(
                        notification.getDaysRemaining().toString()
                );

                notificationTable.addCell(
                        notification.getPriority().name()
                );
            }

            document.add(notificationTable);

            document.add(
                    new Paragraph(" ")
            );

            document.add(
                    new Paragraph(
                            "FINANCIAL STREAKS",
                            sectionFont
                    )
            );

            document.add(
                    new Paragraph(" ")
            );

            PdfPTable streakTable =
                    new PdfPTable(2);

            streakTable.setWidthPercentage(100);

            streakTable.addCell(
                    new PdfPCell(
                            new Paragraph("Metric", headerFont)
                    )
            );

            streakTable.addCell(
                    new PdfPCell(
                            new Paragraph("Value", headerFont)
                    )
            );

            streakTable.setHeaderRows(1);

            streakTable.addCell("Current Logging Streak");

            streakTable.addCell(
                    dashboard.getFinancialStreaks()
                            .getCurrentLoggingStreak()
                            .toString()
            );

            streakTable.addCell("Current No-Spend Streak");

            streakTable.addCell(
                    dashboard.getFinancialStreaks()
                            .getCurrentNoSpendStreak()
                            .toString()
            );

            streakTable.addCell("Motivational Message");

            streakTable.addCell(
                    dashboard.getFinancialStreaks()
                            .getMotivationalMessage()
            );

            document.add(streakTable);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception exception) {

            throw new IllegalStateException(
                    "Failed to generate dashboard PDF.",
                    exception
            );
        }
    }

    private byte[] exportTransactions() {

        List<TransactionResponse> transactions =
                transactionService.getMyTransactions();

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            Document document =
                    new Document(PageSize.A4.rotate());

            PdfWriter.getInstance(
                    document,
                    outputStream
            );

            document.open();

            Font titleFont = new Font(
                    Font.HELVETICA,
                    18,
                    Font.BOLD
            );

            Font headerFont = new Font(
                    Font.HELVETICA,
                    9,
                    Font.BOLD
            );

            Font normalFont = new Font(
                    Font.HELVETICA,
                    8,
                    Font.NORMAL
            );

            document.add(
                    new Paragraph(
                            "FLOWLEDGER TRANSACTIONS",
                            titleFont
                    )
            );

            document.add(
                    new Paragraph(
                            "Generated: "
                                    + LocalDate.now(),
                            normalFont
                    )
            );

            document.add(
                    new Paragraph(" ")
            );

            PdfPTable transactionTable =
                    new PdfPTable(8);

            transactionTable.setWidthPercentage(100);

            transactionTable.setWidths(
                    new float[]{
                            6f,
                            16f,
                            12f,
                            10f,
                            14f,
                            14f,
                            16f,
                            20f
                    }
            );

            transactionTable.addCell(
                    new PdfPCell(
                            new Paragraph("ID", headerFont)
                    )
            );

            transactionTable.addCell(
                    new PdfPCell(
                            new Paragraph("Title", headerFont)
                    )
            );

            transactionTable.addCell(
                    new PdfPCell(
                            new Paragraph("Amount", headerFont)
                    )
            );

            transactionTable.addCell(
                    new PdfPCell(
                            new Paragraph("Type", headerFont)
                    )
            );

            transactionTable.addCell(
                    new PdfPCell(
                            new Paragraph("Category", headerFont)
                    )
            );

            transactionTable.addCell(
                    new PdfPCell(
                            new Paragraph("Payment Method", headerFont)
                    )
            );

            transactionTable.addCell(
                    new PdfPCell(
                            new Paragraph("Transaction Date", headerFont)
                    )
            );

            transactionTable.addCell(
                    new PdfPCell(
                            new Paragraph("Notes", headerFont)
                    )
            );

            transactionTable.setHeaderRows(1);

            for (TransactionResponse transaction
                    : transactions) {

                transactionTable.addCell(
                        transaction.getId().toString()
                );

                transactionTable.addCell(
                        transaction.getTitle()
                );

                transactionTable.addCell(
                        "₹" + transaction.getAmount()
                );

                transactionTable.addCell(
                        transaction.getType().name()
                );

                transactionTable.addCell(
                        transaction.getCategory().name()
                );

                transactionTable.addCell(
                        transaction.getPaymentMethod().name()
                );

                transactionTable.addCell(
                        transaction.getTransactionTimestamp()
                                .toString()
                );

                transactionTable.addCell(
                        transaction.getNotes()
                );
            }

            transactionTable.setSpacingBefore(10);
            transactionTable.setSpacingAfter(10);

            document.add(transactionTable);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception exception) {

            throw new IllegalStateException(
                    "Failed to generate transactions PDF.",
                    exception
            );
        }
    }

    private byte[] exportMonthlySummary() {

        List<MonthlySummaryResponse> summaries =
                dashboardService.getMonthlySummary();

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            Document document = new Document();

            PdfWriter.getInstance(
                    document,
                    outputStream
            );

            document.open();

            Font titleFont = new Font(
                    Font.HELVETICA,
                    18,
                    Font.BOLD
            );

            Font headerFont = new Font(
                    Font.HELVETICA,
                    9,
                    Font.BOLD
            );

            Font normalFont = new Font(
                    Font.HELVETICA,
                    9,
                    Font.NORMAL
            );

            document.add(
                    new Paragraph(
                            "FLOWLEDGER MONTHLY SUMMARY",
                            titleFont
                    )
            );

            document.add(
                    new Paragraph(
                            "Generated: "
                                    + LocalDate.now(),
                            normalFont
                    )
            );

            document.add(
                    new Paragraph(" ")
            );

            PdfPTable summaryTable =
                    new PdfPTable(7);

            summaryTable.setWidthPercentage(100);

            summaryTable.setWidths(
                    new float[]{
                            8f,
                            14f,
                            16f,
                            16f,
                            16f,
                            16f,
                            14f
                    }
            );

            summaryTable.addCell(
                    new PdfPCell(
                            new Paragraph("Year", headerFont)
                    )
            );

            summaryTable.addCell(
                    new PdfPCell(
                            new Paragraph("Month", headerFont)
                    )
            );

            summaryTable.addCell(
                    new PdfPCell(
                            new Paragraph("Income", headerFont)
                    )
            );

            summaryTable.addCell(
                    new PdfPCell(
                            new Paragraph("Expense", headerFont)
                    )
            );

            summaryTable.addCell(
                    new PdfPCell(
                            new Paragraph("Saving", headerFont)
                    )
            );

            summaryTable.addCell(
                    new PdfPCell(
                            new Paragraph("Saving Rate", headerFont)
                    )
            );

            summaryTable.addCell(
                    new PdfPCell(
                            new Paragraph("Status", headerFont)
                    )
            );

            summaryTable.setHeaderRows(1);

            for (MonthlySummaryResponse summary
                    : summaries) {

                summaryTable.addCell(
                        summary.getYear().toString()
                );

                summaryTable.addCell(
                        summary.getMonth().name()
                );

                summaryTable.addCell(
                        "₹" + summary.getIncome()
                );

                summaryTable.addCell(
                        "₹" + summary.getExpense()
                );

                summaryTable.addCell(
                        "₹" + summary.getSaving()
                );

                summaryTable.addCell(
                        summary.getSavingRate()
                                + "%"
                );

                summaryTable.addCell(
                        summary.getStatus().name()
                );
            }

            summaryTable.setSpacingBefore(10);
            summaryTable.setSpacingAfter(10);

            document.add(summaryTable);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception exception) {

            throw new IllegalStateException(
                    "Failed to generate monthly summary PDF.",
                    exception
            );
        }
    }
}
