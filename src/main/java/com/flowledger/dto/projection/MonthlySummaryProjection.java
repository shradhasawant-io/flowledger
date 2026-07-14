package com.flowledger.dto.projection;

import java.math.BigDecimal;

public interface MonthlySummaryProjection {

    Integer getYear();

    Integer getMonth();

    BigDecimal getIncome();

    BigDecimal getExpense();

}