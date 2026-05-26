package com.tracker.savingstracker.service;

import com.opencsv.bean.CsvToBean;
import com.tracker.savingstracker.model.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
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
    public void createAccount(int id, String name, double totBalance, double totProfit, double totDeposit) {
        try {
            Account account = new Account(id, name, totBalance, totProfit, totDeposit);
            accounts.add(account);
            csvWrite.writeEntireAcc(account);

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
