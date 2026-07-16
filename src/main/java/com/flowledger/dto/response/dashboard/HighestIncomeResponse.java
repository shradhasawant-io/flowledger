package com.flowledger.dto.response.dashboard;

import com.flowledger.enums.PaymentMethod;
import com.flowledger.enums.TransactionCategory;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class HighestIncomeResponse {

    private Long id;

    private String title;

    private BigDecimal amount;

    private TransactionCategory category;

    private PaymentMethod paymentMethod;

    private LocalDateTime transactionTimestamp;

}