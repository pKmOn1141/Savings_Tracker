package com.tracker.savingstracker.model;

public record BalanceEntry(
        int id,
        String date,
        double depositAmount,
        double accountBalance,
        double poundChange,
        double percentChange,
        double profit) {
}
