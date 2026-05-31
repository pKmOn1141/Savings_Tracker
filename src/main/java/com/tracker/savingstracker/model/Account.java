package com.tracker.savingstracker.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;

@Setter
@Getter
@Slf4j
public class Account {
    // Store in accountInfo csv
    @CsvBindByName
    private int id;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private double totBalance;
    @CsvBindByName
    private double totProfit;
    @CsvBindByName
    private double totDeposit;

    // Store in entry csv
    private ArrayList<BalanceEntry> entries = new ArrayList<BalanceEntry>();

    // CSV Constructor
    public Account() {

    }

    // Normal Constructor
    public Account(int id, String name, double totBalance, double totProfit, double totDeposit) {
        this.id = id;
        this.name = name;
        this.totBalance = totBalance;
        this.totProfit = totProfit;
        this.totDeposit = totDeposit;
    }

    public int entryCount() {
        return entries.size();
    }

    // Sort by entry ID
    public void sortEntryByID(boolean ascending) {
        if (ascending) {
            entries.sort(Comparator.comparingInt(BalanceEntry::id));
        }
        else {
            entries.sort(Comparator.comparingInt(BalanceEntry::id).reversed());
        }

    }

    // Create the complete entry
    public void createEntry(String name, String date, double depAmount, double accBalance) {
        int id = entryCount();
        double poundChange = 0.0;
        double percChange = 0.0;
        double profit = 0.0;

        if (!entries.isEmpty()) {
            // Get pound + percentage changes
            sortEntryByID(true);
            BalanceEntry lastEntry = entries.getLast();

            poundChange = accBalance - lastEntry.accountBalance();
            percChange = (accBalance - lastEntry.accountBalance())/lastEntry.accountBalance() * 100;
            percChange = (double) Math.round(percChange * 100) /100; // Round to 2dp
            profit = poundChange - depAmount;
        }

        // Update other account information
        this.totBalance = accBalance;
        this.totProfit += poundChange - depAmount;
        this.totDeposit += depAmount;

        addEntry(id, date, depAmount, accBalance, poundChange, percChange, profit);
    }

    // Add a new entry
    public void addEntry(int id,
                         String date,
                         double depositAmount,
                         double accountBalance,
                         double poundChange,
                         double percentChange,
                         double profit) {
        entries.add(new BalanceEntry(id, date, depositAmount, accountBalance, poundChange, percentChange, profit));
    }
}
