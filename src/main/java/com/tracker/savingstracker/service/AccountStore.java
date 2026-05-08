package com.tracker.savingstracker.service;

import com.tracker.savingstracker.model.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AccountStore {
    @Setter
    @Getter
    private ArrayList<Account> accounts = new ArrayList<>();
    private final CsvWrite csvWrite;

    public AccountStore(CsvWrite csvWrite) {
        this.csvWrite = csvWrite;
    }

    // New account
    public boolean createAccount(int id, String name, double totBalance, double totProfit, double totDeposit) {
        try {
            Account account = new Account(id, name, totBalance, totProfit, totDeposit);
            accounts.add(account);
            csvWrite.writeEntireAcc(account);

            return true;
        } catch(Exception e) {
            return false;
        }
    }

}
