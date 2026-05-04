package com.tracker.savingstracker.service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.tracker.savingstracker.model.Account;
import com.tracker.savingstracker.model.BalanceEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class CsvWriter {

    @Value("${app.csv.directory}")
    private String path;

    public CsvWriter() {
    }

    // Write account info to csv
    private void writeAccountInfo(Account account) throws Exception {
        try (Writer writer = new FileWriter(pathGen(account.getName(), "accountInfo.csv"))) {
            StatefulBeanToCsv<Account> beanToCsv = new StatefulBeanToCsvBuilder<Account>(writer).build();

            beanToCsv.write(account);
            log.info("Written account");
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
                log.info("Written entries");
            }
        }
    }

    // Generates the path string for the csv file
    private String pathGen(String account, String type) throws IOException {
        log.info("Generating path for account {}", account);
        String fullPath = new StringBuilder()
                .append(path)
                .append(File.separator)
                .append(account)
                .append(File.separator)
                .append(type)
                .toString();

        log.info("Generated directory {}", fullPath);
        Path directoryPath = Paths.get(fullPath).getParent();
        Files.createDirectories(directoryPath);

        return fullPath;
    }

    public void writeEntireAcc(Account account) throws Exception {
        log.info("Writing account");
        writeAccountInfo(account);
        writeAccountEntries(account);
        log.info("Account written");
    }
}
