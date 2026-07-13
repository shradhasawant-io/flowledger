package com.flowledger.dto.request;

import com.flowledger.enums.PaymentMethod;
import com.flowledger.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionFilterRequest {

    private TransactionType type;

    private PaymentMethod paymentMethod;

    private LocalDate startDate;

    private LocalDate endDate;
}
