package com.flowledger.dto.response;

import com.flowledger.enums.PaymentMethod;
import com.flowledger.enums.RecurringFrequency;
import com.flowledger.enums.TransactionCategory;
import com.flowledger.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecurringTransactionResponse {

    private Long id;

    private String title;

    private String description;

    private BigDecimal amount;

    private TransactionType type;

    private TransactionCategory category;

    private RecurringFrequency frequency;

    private LocalDate startDate;

    private LocalDate nextExecutionDate;

    private Boolean active;

    private PaymentMethod paymentMethod;
}
