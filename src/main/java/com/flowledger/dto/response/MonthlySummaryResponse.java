package com.flowledger.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.Month;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlySummaryResponse {

    private Integer year;

    private Month month;

    private BigDecimal income;

    private BigDecimal expense;

}