package com.tracker.savingstracker.model;

import com.opencsv.bean.CsvBindByName;

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
    private BalanceEntry[] entries;

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

    // Getters/Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public BalanceEntry[] getEntries() {
        return entries;
    }
    public void setEntries(BalanceEntry[] entries) {
        this.entries = entries;
    }
}
