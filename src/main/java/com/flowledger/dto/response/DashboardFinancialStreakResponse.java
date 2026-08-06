package com.flowledger.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardFinancialStreakResponse {

    private Integer currentLoggingStreak;

    private Integer currentNoSpendStreak;

    private String motivationalMessage;

}
