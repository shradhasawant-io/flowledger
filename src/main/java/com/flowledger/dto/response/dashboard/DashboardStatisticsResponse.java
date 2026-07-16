package com.flowledger.dto.response.dashboard;

import java.math.BigDecimal;
import java.time.Month;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardStatisticsResponse {

    private BigDecimal highestIncome;

    private BigDecimal highestExpense;

    private BigDecimal totalSavings;

    private BigDecimal averageMonthlyIncome;

    private BigDecimal averageMonthlyExpense;

    private Month bestMonth;

    private Month worstMonth;

    private BigDecimal averageSaving;

    private Integer profitableMonths;

    private Integer lossMonths;

}