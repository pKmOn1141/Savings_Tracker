package com.tracker.savingstracker.controller;

import com.tracker.savingstracker.model.Account;
import com.tracker.savingstracker.service.AccountStore;
import com.tracker.savingstracker.service.CsvRead;
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

    private final AccountStore accountStore;
    private final CsvRead csvReader;

    public HomeController(AccountStore accountStore, CsvRead csvReader) {
        this.accountStore = accountStore;
        this.csvReader = csvReader;
    }

    @GetMapping("/")
    public String home(Model model) throws Exception {
        log.info("Refreshing home page");
        model.addAttribute("accounts", accountStore.getAccounts());
        return "index";
    }

    @GetMapping("/entries")
    public String viewEntries(@RequestParam String accountName, Model model) {
        Optional<Account> resultAcc = accountStore.findByName(accountName);
        if (resultAcc.isPresent()) {
            Account account = resultAcc.get();
            log.info("Displaying {} account entries", accountName);
            model.addAttribute("entries", account.getEntries());
            model.addAttribute("selectedAccount", account.getName());
        }
        model.addAttribute("accounts", accountStore.getAccounts());
        return "index";
    }

    @PostMapping("/new_account")
    public String createAccount(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam double totBalance,
            @RequestParam double totProfit,
            @RequestParam double totDeposit
    ) {
        accountStore.createAccount(id, name, totBalance, totProfit, totDeposit);
        return "redirect:/";
    }

    @PostMapping("/new_entry")
    public String createEntry(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam String date,
            @RequestParam double depositAmount,
            @RequestParam double accountBalance,
            @RequestParam double poundChange,
            @RequestParam double percentChange
    ) {
        Optional<Account> searchResult = accountStore.findByName(name);
        if (searchResult.isPresent()) {
            Account targetAccount = searchResult.get();
            targetAccount.addEntry(id, date, depositAmount, accountBalance, poundChange, percentChange);

        }
        else {
            log.warn("Account with name {} not found", name);
        }
        return "redirect:/";
    }
}
