package com.tracker.savingstracker.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.tracker.savingstracker.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class CsvRead {

    @Value("${app.csv.directory}")
    private String path;
    private final CsvPathGen pathGen;
    private final AccountStore accountStore;

    public CsvRead(CsvPathGen pathGen, AccountStore accountStore) {
        this.pathGen = pathGen;
        this.accountStore = accountStore;
    }

    // Run at the start of the application
    @PostConstruct
    public void init() throws Exception {
        readAccountsInfo();
    }

    public void readAccountsInfo() throws Exception {

        Path directory = Paths.get(path);

        if  (!Files.exists(directory) || !Files.isDirectory(directory)) {
            log.warn("No directories exist");
            return;
        }

        log.info("Importing directories");
        try (Stream<Path> accountDirs = Files.list(Paths.get(path))) {

            accountDirs.filter(Files::isDirectory).forEach(accountDir -> {

                try (Stream<Path> files = Files.list(accountDir)) {
                    files.filter(file -> file.toString().endsWith("accountInfo.csv"))
                            .forEach(csvFile -> {

                                try (Reader reader = Files.newBufferedReader(csvFile)) {
                                    CsvToBean<Account> csvToBean = new CsvToBeanBuilder<Account>(reader)
                                            .withType(Account.class)
                                            .withIgnoreLeadingWhiteSpace(true)
                                            .build();
                                    List<Account> accounts = csvToBean.parse();
                                    accountStore.addAccount(accounts.getFirst());
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                log.info("Loaded account {} from CSV", accountDir.getFileName().toString());

                            });
                } catch (IOException e) {
                    log.error("Error reading account directory {}", accountDir, e);
                }
            });
        }

        accountStore.sortByID();
    }

}
