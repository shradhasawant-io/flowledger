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
import com.flowledger.enums.TransactionCategory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.time.LocalDateTime;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class DevelopmentDataInitializer implements CommandLineRunner {

    private static final String DEV_USER_EMAIL = "dhruv@example.com";

    private static final List<SampleTransaction> INCOME_SAMPLES = List.of(

            new SampleTransaction("Salary", TransactionCategory.SALARY),

            new SampleTransaction("Freelance Payment", TransactionCategory.FREELANCE),

            new SampleTransaction("Business Profit", TransactionCategory.BUSINESS),

            new SampleTransaction("Investment Return", TransactionCategory.INVESTMENT),

            new SampleTransaction("Gift Received", TransactionCategory.GIFT)

    );

    private static final List<SampleTransaction> EXPENSE_SAMPLES = List.of(

            new SampleTransaction("Groceries", TransactionCategory.GROCERIES),

            new SampleTransaction("Fuel", TransactionCategory.FUEL),

            new SampleTransaction("Coffee", TransactionCategory.FOOD),

            new SampleTransaction("Restaurant", TransactionCategory.FOOD),

            new SampleTransaction("Electricity Bill", TransactionCategory.UTILITIES),

            new SampleTransaction("Internet Bill", TransactionCategory.UTILITIES),

            new SampleTransaction("Movie Tickets", TransactionCategory.ENTERTAINMENT),

            new SampleTransaction("Uber", TransactionCategory.TRAVEL),

            new SampleTransaction("Mobile Recharge", TransactionCategory.UTILITIES),

            new SampleTransaction("Rent", TransactionCategory.RENT),

            new SampleTransaction("Pharmacy", TransactionCategory.HEALTHCARE),

            new SampleTransaction("Shopping", TransactionCategory.SHOPPING)

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

    private SampleTransaction getRandomIncomeSample() {

        int index = random.nextInt(INCOME_SAMPLES.size());

        return INCOME_SAMPLES.get(index);
    }

    private SampleTransaction getRandomExpenseSample() {

        int index = random.nextInt(EXPENSE_SAMPLES.size());

        return EXPENSE_SAMPLES.get(index);
    }

    private BigDecimal getRandomIncomeAmount() {

        int amount = random.nextInt(10_000, 100_001);

        return BigDecimal.valueOf(amount);

    }

    private BigDecimal getRandomExpenseAmount() {

        int amount = random.nextInt(100, 20_001);

        return BigDecimal.valueOf(amount);

    }

    private LocalDateTime getRandomTimestamp() {

        return LocalDateTime.now()
                .minusDays(random.nextInt(120))
                .minusHours(random.nextInt(24))
                .minusMinutes(random.nextInt(60));
    }

    private Transaction createIncomeTransaction(User user) {

        SampleTransaction sample = getRandomIncomeSample();

        Transaction transaction = new Transaction();

        transaction.setTitle(sample.title());
        transaction.setCategory(sample.category());
        transaction.setAmount(getRandomIncomeAmount());
        transaction.setTransactionTimestamp(getRandomTimestamp());
        transaction.setType(TransactionType.INCOME);
        transaction.setPaymentMethod(PaymentMethod.UPI);
        transaction.setUser(user);
        transaction.setNotes("Sample development data");

        return transaction;
    }

    private Transaction createExpenseTransaction(User user) {

        SampleTransaction sample = getRandomExpenseSample();

        Transaction transaction = new Transaction();

        transaction.setTitle(sample.title());
        transaction.setCategory(sample.category());
        transaction.setAmount(getRandomExpenseAmount());
        transaction.setAmount(getRandomExpenseAmount());
        transaction.setTransactionTimestamp(getRandomTimestamp());
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

