package com.flowledger.dto.response;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialStreakResponse {

    private Integer currentLoggingStreak;

    private Integer longestLoggingStreak;

    private Integer currentNoSpendStreak;

    private Integer longestNoSpendStreak;

    private String motivationalMessage;

}
