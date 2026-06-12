package com.tracker.savingstracker.service;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.tracker.savingstracker.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class CsvDataRead {

    @Value("${app.csv.directory}")
    private String path;
    private final AccountStore accountStore;

    public CsvDataRead(AccountStore accountStore) {
        this.accountStore = accountStore;
    }

    // Run at the start of the application
    @PostConstruct
    public void init() throws Exception {
        readAccountsInfo();
        readEntries();
        readNotes();
        accountStore.sortByID();
    }

    // Reading account information from both csv files
    public void readAccountsInfo() throws Exception {

        // Check that the paths exist
        Path directory = Paths.get(path);

        if  (!Files.exists(directory) || !Files.isDirectory(directory)) {
            log.warn("No directories exist");
            return;
        }

        log.info("Importing directories");
        try (Stream<Path> accountDirs = Files.list(Paths.get(path))) {
            // Iterate through each file directory
            accountDirs.filter(Files::isDirectory).forEach(accountDir -> {
                try (Stream<Path> files = Files.list(accountDir)) {
                    // Filter the files to "accountInfo.csv", then load
                    files.filter(file -> file.toString().endsWith("accountInfo.csv")).forEach(csvFile -> {
                        // Read the selected file
                        try (Reader reader = Files.newBufferedReader(csvFile)) {
                            // Turn into a class
                            CsvToBean<Account> csvToBean = new CsvToBeanBuilder<Account>(reader)
                                    .withType(Account.class)
                                    .withIgnoreLeadingWhiteSpace(true)
                                    .build();
                            // Build account
                            List<Account> accounts = csvToBean.parse();
                            Account newAccount = accounts.getFirst();
                            // Add to accountStore
                            accountStore.addAccount(newAccount);
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
    }

    // Read "entries.csv"
    public void readEntries() throws Exception {
        int accId = 0;

        // Check that the paths exist
        Path directory = Paths.get(path);

        if  (!Files.exists(directory) || !Files.isDirectory(directory)) {
            log.warn("No directories exist");
            return;
        }

        try (Stream<Path> accountDirs = Files.list(Paths.get(path))) {

            // Iterate through each file directory
            accountDirs.filter(Files::isDirectory).forEach(accountDir -> {
                try (Stream<Path> files = Files.list(accountDir)) {
                    // Filter the file to "entries.csv", then load
                    files.filter(file -> file.toString().endsWith("entries.csv")).forEach(csvFile -> {
                        // Read the selected file
                        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile.toFile()))) {
                            log.info("Loaded entries from {} ", accountDir.getFileName());
                            String[] entry;
                            // Store each line in the array
                            while ((entry = csvReader.readNext()) != null) {
                                // If not header, add entry
                                if (!entry[0].equals("id")) {
                                    // Search for account obj based off name
                                    Optional<Account> searchAcc = accountStore.findByName(accountDir.getFileName().toString());
                                    if (searchAcc.isPresent()) {
                                        // Create entry in the array
                                        Account newAccount = searchAcc.get();
                                        loadEntry(newAccount, entry);
                                    }
                                }
                            }
                        } catch (IOException | CsvValidationException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    // Load from the data array into the account class
    public void loadEntry(Account account, String[] data) {
        account.addEntry(
                Integer.parseInt(data[0]),
                data[1],
                Double.parseDouble(data[2]),
                Double.parseDouble(data[3]),
                Double.parseDouble(data[4]),
                Double.parseDouble(data[5]),
                Double.parseDouble(data[6])
        );
    }

    public void readNotes() throws Exception{

        // Check that the paths exist
        Path directory = Paths.get(path);

        if  (!Files.exists(directory) || !Files.isDirectory(directory)) {
            log.warn("No directories exist");
            return;
        }

        try (Stream<Path> accountDirs = Files.list(Paths.get(path))) {
            // Iterate through each file directory
            accountDirs.filter(Files::isDirectory).forEach(accountDir -> {
                try (Stream<Path> files = Files.list(accountDir)) {
                    // Filter the files to "notes.txt", then load
                    files.filter(file -> file.toString().endsWith("notes.txt")).forEach(txtFile -> {
                        // Read the selected file
                        try (BufferedReader reader = Files.newBufferedReader(txtFile)) {
                            StringBuilder line = new StringBuilder();
                            String newLine;
                            while ((newLine = reader.readLine()) != null) {
                                line.append(newLine).append("\n");
                            }
                            // Search for account obj based off name
                            Optional<Account> searchAcc = accountStore.findByName(accountDir.getFileName().toString());
                            if (searchAcc.isPresent()) {
                                // Save notes to account
                                Account newAccount = searchAcc.get();
                                newAccount.setNotes(line.toString());
                            }

                            log.info("Finished reading notes from {} ", accountDir.getFileName());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
