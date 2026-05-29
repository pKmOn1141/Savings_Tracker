package com.tracker.savingstracker.service;

import com.tracker.savingstracker.model.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

@Slf4j
@Component
public class AccountStore {
    @Setter
    @Getter
    private ArrayList<Account> accounts = new ArrayList<>();
    private final CsvDataWrite csvDataWrite;

    public AccountStore(CsvDataWrite csvDataWrite) {
        this.csvDataWrite = csvDataWrite;
    }

    // Sort by account ID
    public void sortByID() {
        accounts.sort(Comparator.comparingInt(Account::getId));
        log.info("Sorted accounts by ID");
    }

    // New account
    public void createAccount(String name) {
        // New account info
        int id = accounts.size()+1;
        double totBalance = 0.0;
        double totProfit = 0.0;
        double totDeposit = 0.0;

        try {
            Account account = new Account(id, name, totBalance, totProfit, totDeposit);
            accounts.add(account);
            csvDataWrite.writeEntireAcc(account);

        } catch(Exception _) {
        }
    }

    // Add an account to the list
    public void addAccount(Account account) throws Exception {
        accounts.add(account);
    }

    // Search for account by account name
    public Optional<Account> findByName(String name) {
        for (Account account : accounts) {
            if (account.getName().equals(name)) {
                log.info("Account found with name {}", name);
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

}
