package com.tracker.savingstracker.controller;

import com.tracker.savingstracker.model.Account;
import com.tracker.savingstracker.service.AccountStore;
import com.tracker.savingstracker.service.CsvDataWrite;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Controller
public class HomeController {

    // INITIALISING OBJECTS/VARIABLES

    private final AccountStore accountStore;
    private final CsvDataWrite csvDataWrite;

    public HomeController(AccountStore accountStore, CsvDataWrite csvDataWrite) {
        this.accountStore = accountStore;
        this.csvDataWrite = csvDataWrite;
    }

    // DISPLAY FUNCTIONS

    // Display main page
    @GetMapping("/")
    public String home(Model model) throws Exception {
        log.info(" ");
        log.info("Refreshing home page");
        model.addAttribute("accounts", accountStore.getAccounts());
        return "index";
    }

    // Display entries
    @GetMapping("/entries")
    public String viewEntries(@RequestParam String accountName, Model model) {
        Optional<Account> resultAcc = accountStore.findByName(accountName);
        if (resultAcc.isPresent()) {
            Account account = resultAcc.get();
            account.sortEntryByID(false);
            log.info("Displaying {} account entries", accountName);
            model.addAttribute("entries", account.getEntries());
            model.addAttribute("selectedAccount", account.getName());
        }
        model.addAttribute("accounts", accountStore.getAccounts());
        return "index";
    }

    // CREATE FUNCTIONS

    // Create a new account
    @PostMapping("/new_account")
    public String createAccount(
            @RequestParam String name
    ) {
        accountStore.createAccount(name);
        return "redirect:/";
    }

    // Create a new entry
    @PostMapping("/new_entry")
    public String createEntry(
            @RequestParam String name,
            @RequestParam String date,
            @RequestParam double depositAmount,
            @RequestParam double accountBalance
    ) {
        Optional<Account> searchResult = accountStore.findByName(name);
        if (searchResult.isPresent()) {
            Account targetAccount = searchResult.get();
            // Create Entry
            targetAccount.createEntry(name, date, depositAmount, accountBalance);
            // Rewrite account to csv
            try {
                csvDataWrite.writeEntireAcc(targetAccount);
                log.info("New entry added to {}", targetAccount.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {
            log.warn("Account with name {} not found", name);
        }
        return "redirect:/";
    }
}
