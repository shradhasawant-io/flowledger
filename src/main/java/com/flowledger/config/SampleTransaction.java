package com.flowledger.config;

import com.flowledger.enums.TransactionCategory;

public record SampleTransaction(

        String title,

        TransactionCategory category

) {
}