package com.tracker.savingstracker.service;

import com.tracker.savingstracker.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AccountStore {
    private ArrayList<Account> accounts = new ArrayList<>();
    private final CsvWriter csvWriter;

    public AccountStore(CsvWriter csvWriter) {
        this.csvWriter = csvWriter;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    // New account
    public boolean createAccount(int id, String name, double totBalance, double totProfit, double totDeposit) {
        try {
            Account account = new Account(id, name, totBalance, totProfit, totDeposit);
            accounts.add(account);
            csvWriter.writeEntireAcc(account);

            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
