package com.flowledger.dto.response;

import com.flowledger.enums.PaymentMethod;
import com.flowledger.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.flowledger.enums.TransactionCategory;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private Long id;

    private String title;

    private BigDecimal amount;

    private TransactionType type;

    private TransactionCategory category;

    private PaymentMethod paymentMethod;

    private LocalDate transactionDate;

    private String notes;
}
