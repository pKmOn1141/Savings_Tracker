package com.tracker.savingstracker.service;

import com.tracker.savingstracker.model.Account;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountStore {
    private ArrayList<Account> accounts = new ArrayList<>();

    public AccountStore() {
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
            accounts.add(new Account(id, name, totBalance, totProfit, totDeposit));
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
