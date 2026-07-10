package com.flowledger.dto.request;

import com.flowledger.enums.PaymentMethod;
import com.flowledger.enums.TransactionType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class CreateTransactionRequest {


    @NotBlank
    @Size(max = 100)
    private String title;

    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 17, fraction = 2)
    private BigDecimal amount;

    @NotNull
    private TransactionType type;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private LocalDate transactionDate;

    @Size(max = 500)
    private String notes;
}
