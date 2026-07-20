package com.flowledger.service.impl;

import com.flowledger.dto.request.CreateBudgetRequest;
import com.flowledger.dto.request.UpdateBudgetRequest;
import com.flowledger.dto.response.BudgetProgressResponse;
import com.flowledger.dto.response.BudgetResponse;
import com.flowledger.dto.response.BudgetSuggestion;
import com.flowledger.dto.response.BudgetSuggestionResponse;
import com.flowledger.entity.Budget;
import com.flowledger.entity.User;
import com.flowledger.enums.*;
import com.flowledger.mapper.BudgetMapper;
import com.flowledger.repository.BudgetRepository;
import com.flowledger.repository.TransactionRepository;
import com.flowledger.service.AuthenticatedUserService;
import com.flowledger.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    private final BudgetMapper budgetMapper;

    private final AuthenticatedUserService authenticatedUserService;

    private final TransactionRepository transactionRepository;

    @Override
    public BudgetResponse createBudget(CreateBudgetRequest request) {

        User currentUser = authenticatedUserService.getCurrentUser();

        boolean budgetExists =
                budgetRepository.existsByUserAndCategoryAndPeriod(
                        currentUser,
                        request.getCategory(),
                        request.getPeriod()
                );
        if (budgetExists) {
            throw new IllegalArgumentException(
                    "Budget already exists for the selected category and period."
            );
        }

        Budget budget = Budget.builder()
                .budgetAmount(request.getBudgetAmount())
                .category(request.getCategory())
                .period(request.getPeriod())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .user(currentUser)
                .build();

        Budget savedBudget = budgetRepository.save(budget);

        return budgetMapper.toResponse(savedBudget);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BudgetResponse> getAllBudgets() {

        User currentUser = authenticatedUserService.getCurrentUser();

        return budgetRepository.findByUser(currentUser)
                .stream()
                .map(budgetMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BudgetResponse getBudgetById(Long budgetId) {

        User currentUser = authenticatedUserService.getCurrentUser();

        Budget budget = budgetRepository.findByIdAndUser(budgetId, currentUser)
                .orElseThrow(() ->
                        new IllegalArgumentException("Budget not found.")
                );

        return budgetMapper.toResponse(budget);
    }

    @Override
    public BudgetResponse updateBudget(Long budgetId,
                                       UpdateBudgetRequest request) {

        User currentUser = authenticatedUserService.getCurrentUser();

        Budget budget = budgetRepository.findByIdAndUser(
                budgetId,
                currentUser
        ).orElseThrow(() ->
                new IllegalArgumentException("Budget not found.")
        );

        boolean duplicateBudget =
                budgetRepository.existsByUserAndCategoryAndPeriodAndIdNot(
                        currentUser,
                        request.getCategory(),
                        request.getPeriod(),
                        budgetId
                );

        if (duplicateBudget) {
            throw new IllegalArgumentException(
                    "Budget already exists for the selected category and period."
            );
        }

        budget.setBudgetAmount(request.getBudgetAmount());
        budget.setCategory(request.getCategory());
        budget.setPeriod(request.getPeriod());
        budget.setStartDate(request.getStartDate());
        budget.setEndDate(request.getEndDate());

        Budget updatedBudget = budgetRepository.save(budget);

        return budgetMapper.toResponse(updatedBudget);
    }

    @Override
    public void deleteBudget(Long budgetId) {

        User currentUser = authenticatedUserService.getCurrentUser();

        Budget budget = budgetRepository.findByIdAndUser(
                budgetId,
                currentUser
        ).orElseThrow(() ->
                new IllegalArgumentException("Budget not found.")
        );

        budgetRepository.delete(budget);
    }

    @Override
    @Transactional(readOnly = true)
    public BudgetProgressResponse getBudgetProgress(Long budgetId) {

        User currentUser = authenticatedUserService.getCurrentUser();

        Budget budget = budgetRepository.findByIdAndUser(
                budgetId,
                currentUser
        ).orElseThrow(() ->
                new IllegalArgumentException("Budget not found.")
        );

        return calculateBudgetProgress(currentUser, budget);


    }

    private BudgetProgressResponse calculateBudgetProgress(
            User currentUser,
            Budget budget
    ) {

        BigDecimal spentAmount = transactionRepository.getTotalAmountByCategoryAndDateRange(
                currentUser,
                TransactionType.EXPENSE,
                budget.getCategory(),
                budget.getStartDate().atStartOfDay(),
                budget.getEndDate().atTime(LocalTime.MAX)
        );

        BigDecimal remainingAmount =
                budget.getBudgetAmount().subtract(spentAmount);

        BigDecimal percentageUsed;

        if (budget.getBudgetAmount().compareTo(BigDecimal.ZERO) == 0) {
            percentageUsed = BigDecimal.ZERO;
        } else {
            percentageUsed = spentAmount
                    .multiply(BigDecimal.valueOf(100))
                    .divide(
                            budget.getBudgetAmount(),
                            2,
                            RoundingMode.HALF_UP
                    );
        }

        BudgetStatus status;

        if (percentageUsed.compareTo(BigDecimal.valueOf(100)) > 0) {
            status = BudgetStatus.EXCEEDED;
        } else if (percentageUsed.compareTo(BigDecimal.valueOf(80)) >= 0) {
            status = BudgetStatus.WARNING;
        } else {
            status = BudgetStatus.ON_TRACK;
        }
        Boolean showAlert;
        BudgetAlertType alertType;
        String alertMessage;

        if (percentageUsed.compareTo(BigDecimal.valueOf(100)) >= 0) {

            showAlert = true;
            alertType = BudgetAlertType.EXCEEDED;
            alertMessage = "You have exceeded your budget.";

        } else if (percentageUsed.compareTo(BigDecimal.valueOf(80)) >= 0) {

            showAlert = true;
            alertType = BudgetAlertType.WARNING;
            alertMessage = "You have used over 80% of your budget.";

        } else {

            showAlert = false;
            alertType = BudgetAlertType.NONE;
            alertMessage = "You are well within your budget.";
        }

        return BudgetProgressResponse.builder()
                .budgetId(budget.getId())
                .category(budget.getCategory())
                .period(budget.getPeriod())
                .budgetAmount(budget.getBudgetAmount())
                .spentAmount(spentAmount)
                .remainingAmount(remainingAmount)
                .percentageUsed(percentageUsed)
                .status(status)
                .showAlert(showAlert)
                .alertType(alertType)
                .alertMessage(alertMessage)

                .build();

    }

    @Override
    @Transactional(readOnly = true)
    public BudgetSuggestionResponse getBudgetSuggestions(Long budgetId) {

        User currentUser = authenticatedUserService.getCurrentUser();

        Budget budget = budgetRepository.findByIdAndUser(budgetId, currentUser)
                .orElseThrow(() -> new IllegalArgumentException("Budget not found."));

        BudgetProgressResponse progress =
                calculateBudgetProgress(currentUser, budget);

        List<BudgetSuggestion> suggestions =
                generateSuggestions(progress);


        return BudgetSuggestionResponse.builder()
                .budgetId(budget.getId())
                .category(budget.getCategory())
                .budgetAmount(budget.getBudgetAmount())
                .spentAmount(progress.getSpentAmount())
                .remainingAmount(progress.getRemainingAmount())
                .usagePercentage(progress.getPercentageUsed())
                .suggestions(suggestions)
                .build();
    }

    private List<BudgetSuggestion> generateSuggestions(
            BudgetProgressResponse progress) {

        List<BudgetSuggestion> suggestions = new ArrayList<>();

        BigDecimal usagePercentage = progress.getPercentageUsed();

        if (usagePercentage.compareTo(BigDecimal.valueOf(100)) >= 0) {

            suggestions.add(
                    BudgetSuggestion.builder()
                            .type(SuggestionType.REDUCE_SPENDING)
                            .priority(SuggestionPriority.CRITICAL)
                            .title("Budget Exceeded")
                            .description("You have exceeded your budget for this category.")
                            .action("Reduce spending immediately or consider increasing next month's budget.")
                            .build()
            );
        }

        if (usagePercentage.compareTo(BigDecimal.valueOf(90)) >= 0
                && usagePercentage.compareTo(BigDecimal.valueOf(100)) < 0) {

            suggestions.add(
                    BudgetSuggestion.builder()
                            .type(SuggestionType.REDUCE_SPENDING)
                            .priority(SuggestionPriority.HIGH)
                            .title("Budget Almost Exhausted")
                            .description("You have already used more than 90% of your budget.")
                            .action("Avoid unnecessary spending for the remaining budget period.")
                            .build()
            );
        }

        if (usagePercentage.compareTo(BigDecimal.valueOf(50)) < 0) {

            suggestions.add(
                    BudgetSuggestion.builder()
                            .type(SuggestionType.SAVE_SURPLUS)
                            .priority(SuggestionPriority.LOW)
                            .title("Budget Under Control")
                            .description("You are spending well within your planned budget.")
                            .action("Consider transferring part of your remaining budget to savings.")
                            .build()
            );
        }

        if (suggestions.isEmpty()) {

            suggestions.add(
                    BudgetSuggestion.builder()
                            .type(SuggestionType.MAINTAIN_BUDGET)
                            .priority(SuggestionPriority.MEDIUM)
                            .title("Keep It Up")
                            .description("Your spending is currently balanced.")
                            .action("Continue following your current spending pattern.")
                            .build()
            );
        }

        return suggestions;
    }
}

