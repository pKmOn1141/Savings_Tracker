package com.tracker.savingstracker.controller;

import com.tracker.savingstracker.service.AccountStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final AccountStore accountStore;

    public HomeController(AccountStore accountStore) {
        this.accountStore = accountStore;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("accounts", accountStore.getAccounts());
        return "index";
    }

    @PostMapping("/new_accounts")
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

    @PostMapping("new_entry")
    public String createEntry(
            @RequestParam String date,
            @RequestParam double depositAmount,
            @RequestParam double accountBalance,
            @RequestParam double poundChange,
            @RequestParam double percentChange
    ) {
        return "redirect:/";
    }
}
