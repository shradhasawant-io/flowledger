package com.flowledger.specification;

import com.flowledger.entity.Transaction;
import com.flowledger.entity.User;
import com.flowledger.enums.PaymentMethod;
import com.flowledger.enums.TransactionType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TransactionSpecification {

    public static Specification<Transaction> hasType(
            TransactionType type
    ) {

        if (type == null) {
            return null;
        }

        return (root, query, builder) ->
                builder.equal(root.get("type"), type);
    }

    public static Specification<Transaction> hasPaymentMethod(
            PaymentMethod paymentMethod
    ) {

        if (paymentMethod == null) {
            return null;
        }

        return (root, query, builder) ->
                builder.equal(root.get("paymentMethod"), paymentMethod);
    }

    public static Specification<Transaction> hasStartDate(
            LocalDate startDate
    ) {

        if (startDate == null) {
            return null;
        }

        return (root, query, builder) ->
                builder.greaterThanOrEqualTo(root.get("transactionDate"), startDate);
    }

    public static Specification<Transaction> hasEndDate(
            LocalDate endDate
    ) {

        if (endDate == null) {
            return null;
        }

        return (root, query, builder) ->
                builder.lessThanOrEqualTo(root.get("transactionDate"), endDate);
    }

    public static Specification<Transaction> belongsToUser(
            User user
    ) {

        if (user == null) {
            return null;
        }

        return (root, query, builder) ->
                builder.equal(root.get("user"), user);
    }
}
