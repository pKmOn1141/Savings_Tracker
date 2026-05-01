package com.tracker.savingstracker.service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.tracker.savingstracker.model.Account;
import com.tracker.savingstracker.model.BalanceEntry;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class CsvReader {
    public CsvReader() {
    }

    // Write account info to csv
    private void writeAccountInfo(Account account) throws Exception {
        try (Writer writer = new FileWriter("accountInfo.csv")) {
            StatefulBeanToCsv<Account> beanToCsv = new StatefulBeanToCsvBuilder<Account>(writer).build();

            beanToCsv.write(account);
        }
    }

    // Write account entries to csv
    private void writeAccountEntries(BalanceEntry[] entries) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter("entries.csv"))) {
            writer.writeNext(new String[]{
                    "date", "depositAmount", "accountBalance", "poundChange", "percentChange"
            });

            for (BalanceEntry entry : entries) {
                writer.writeNext(new String[]{
                        entry.date(),
                        String.valueOf(entry.depositAmount()),
                        String.valueOf(entry.accountBalance()),
                        String.valueOf(entry.poundChange()),
                        String.valueOf(entry.percentChange())

                });
            }
        }
    }

    // Generates the path string for the csv file
    private String pathGen(String name) {

    }

    public void writeEntireAcc(Account account) throws IOException {
        private String
    }
}
