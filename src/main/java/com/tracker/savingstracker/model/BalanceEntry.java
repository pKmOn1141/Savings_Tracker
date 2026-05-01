package com.tracker.savingstracker.model;

public record BalanceEntry(
        String date,
        double depositAmount,
        double accountBalance,
        double poundChange,
        double percentChange) {
}
