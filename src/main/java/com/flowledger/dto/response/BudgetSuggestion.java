package com.flowledger.dto.response;

import com.flowledger.enums.SuggestionPriority;
import com.flowledger.enums.SuggestionType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetSuggestion {

    private SuggestionType type;

    private SuggestionPriority priority;

    private String title;

    private String description;

    private String action;
}
