package com.tracker.savingstracker.service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.tracker.savingstracker.model.Account;
import com.tracker.savingstracker.model.BalanceEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

@Service
public class CsvReader {

    @Value("${app.csv.directory}")
    private String path;

    public CsvReader() {
    }

    // Write account info to csv
    private void writeAccountInfo(Account account) throws Exception {
        try (Writer writer = new FileWriter(pathGen(account.getName(), "accountInfo.csv"))) {
            StatefulBeanToCsv<Account> beanToCsv = new StatefulBeanToCsvBuilder<Account>(writer).build();

            beanToCsv.write(account);
        }
    }

    // Write account entries to csv
    private void writeAccountEntries(Account account) throws IOException {
        BalanceEntry[] entries = account.getEntries();

        try (CSVWriter writer = new CSVWriter(new FileWriter(pathGen(account.getName(), "entries.csv")))) {
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
    private String pathGen(String account, String type) throws IOException {
        return new StringBuilder()
                .append(path)
                .append(File.separator)
                .append(account)
                .append(File.separator)
                .append(type)
                .toString();
    }

    public void writeEntireAcc(Account account) throws Exception {
        writeAccountInfo(account);
        writeAccountEntries(account);
    }
}
