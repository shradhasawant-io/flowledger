package com.flowledger.dto.response.dashboard;

import com.flowledger.enums.PaymentMethod;
import com.flowledger.enums.TransactionCategory;
import com.flowledger.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class RecentTransactionResponse {

    private Long id;

    private String title;

    private BigDecimal amount;

    private TransactionType type;

    private TransactionCategory category;

    private PaymentMethod paymentMethod;

    private LocalDateTime transactionTimestamp;

}