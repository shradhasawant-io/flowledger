package com.flowledger.dto.response;

import lombok.*;
import com.flowledger.enums.MonthlyStatus;
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

    private BigDecimal saving;

    private BigDecimal savingRate;

    private MonthlyStatus status;

}