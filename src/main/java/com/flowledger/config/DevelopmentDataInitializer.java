package com.flowledger.config;

import com.flowledger.entity.Transaction;
import com.flowledger.entity.User;
import com.flowledger.enums.PaymentMethod;
import com.flowledger.enums.TransactionType;
import com.flowledger.repository.TransactionRepository;
import com.flowledger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class DevelopmentDataInitializer implements CommandLineRunner {

    private static final String DEV_USER_EMAIL = "dhruv@example.com";

    private static final List<String> INCOME_TITLES = List.of(
            "Salary",
            "Freelance Payment",
            "Bonus",
            "Investment Return",
            "Gift Received"
    );

    private static final List<String> EXPENSE_TITLES = List.of(
            "Groceries",
            "Fuel",
            "Coffee",
            "Restaurant",
            "Electricity Bill",
            "Internet Bill",
            "Movie Tickets",
            "Uber",
            "Mobile Recharge",
            "Rent",
            "Pharmacy",
            "Shopping"
    );

    private final Random random = new Random();

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public void run(String... args) {

        log.info("Checking development data...");

        User user = findDevelopmentUser();

        if (user == null) {
            return;
        }

        if (alreadySeeded(user)) {
            return;
        }

        generateSampleTransactions(user);
    }

    private boolean alreadySeeded(User user) {

        long transactionCount =
                transactionRepository.countByUser(user);

        if (transactionCount >= 40) {

            log.info(
                    "Development data already exists ({} transactions). Skipping initialization.",
                    transactionCount
            );

            return true;
        }

        return false;
    }

    private String getRandomIncomeTitle() {

        int index = random.nextInt(INCOME_TITLES.size());

        return INCOME_TITLES.get(index);

    }

    private String getRandomExpenseTitle() {

        int index = random.nextInt(EXPENSE_TITLES.size());

        return EXPENSE_TITLES.get(index);
    }

    private BigDecimal getRandomIncomeAmount() {

        int amount = random.nextInt(10_000, 100_001);

        return BigDecimal.valueOf(amount);

    }

    private BigDecimal getRandomExpenseAmount() {

        int amount = random.nextInt(100, 20_001);

        return BigDecimal.valueOf(amount);

    }

    private LocalDate getRandomDate() {

        int daysAgo = random.nextInt(90);

        return LocalDate.now().minusDays(daysAgo);

    }

    private Transaction createIncomeTransaction(User user) {

        Transaction transaction = new Transaction();

        transaction.setTitle(getRandomIncomeTitle());
        transaction.setAmount(getRandomIncomeAmount());
        transaction.setTransactionDate(getRandomDate());
        transaction.setType(TransactionType.INCOME);
        transaction.setPaymentMethod(PaymentMethod.UPI);
        transaction.setUser(user);
        transaction.setNotes("Sample development data");

        return transaction;
    }

    private Transaction createExpenseTransaction(User user) {

        Transaction transaction = new Transaction();

        transaction.setTitle(getRandomExpenseTitle());
        transaction.setAmount(getRandomExpenseAmount());
        transaction.setTransactionDate(getRandomDate());
        transaction.setType(TransactionType.EXPENSE);
        transaction.setPaymentMethod(PaymentMethod.UPI);
        transaction.setUser(user);
        transaction.setNotes("Sample development data");

        return transaction;

    }

    private void generateSampleTransactions(User user) {

        log.info("Generating sample transactions...");

        List<Transaction> transactions = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            transactions.add(createIncomeTransaction(user));
        }

        for (int i = 0; i < 40; i++) {
            transactions.add(createExpenseTransaction(user));
        }

        transactionRepository.saveAll(transactions);

        log.info("Successfully generated {} sample transactions.", transactions.size());
    }

    private User findDevelopmentUser() {

        Optional<User> optionalUser =
                userRepository.findByEmail(DevelopmentDataInitializer.DEV_USER_EMAIL);

        if (optionalUser.isEmpty()) {
            log.warn(
                    "Development user '{}' not found. Skipping sample data initialization.",
                    DevelopmentDataInitializer.DEV_USER_EMAIL
            );
            return null;
        }

        User user = optionalUser.get();

        log.info("Development user found: {}", user.getEmail());

        return user;
    }

}

