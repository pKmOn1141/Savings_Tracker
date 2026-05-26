package com.tracker.savingstracker.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class Account {
    // Getters/Setters
    // Store in accountInfo csv
    @Getter
    @Setter
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
    @Getter
    @Setter
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getTotBalance() {
        return totBalance;
    }
    public void setTotBalance(double totBalance) {
        this.totBalance = totBalance;
    }

    public double getTotProfit() {
        return totProfit;
    }
    public void setTotProfit(double totProfit) {
        this.totProfit = totProfit;
    }

    public double getTotDeposit() {
        return totDeposit;
    }
    public void setTotDeposit(double totDeposit) {
        this.totDeposit = totDeposit;
    }

    // Add a new entry
    public void addEntry(int id,
                         String date,
                         double depositAmount,
                         double accountBalance,
                         double poundChange,
                         double percentChange) {
        entries.add(new BalanceEntry(id, date, depositAmount, accountBalance, poundChange, percentChange));
        log.info("Balance entry added to account {}", name);
    }
}
