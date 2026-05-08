package com.tracker.savingstracker.service;

import com.tracker.savingstracker.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;

@Slf4j
@Service
public class CsvRead {

    @Value("${app.csv.directory}")
    private String path;
    private final CsvPathGen pathGen;

    public CsvRead(CsvPathGen pathGen) {
        this.pathGen = pathGen;
    }

    private void readAccountsInfo(ArrayList<Account> accountStore) throws Exception {

        try (Reader reader = new FileReader(pathGen.pathGen()))
    }

}
