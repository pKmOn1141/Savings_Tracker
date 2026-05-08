package com.tracker.savingstracker.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class CsvPathGen {

    @Value("${app.csv.directory}")
    private String path;

    // Generates the path string for the csv file
    public String pathGen(String account, String type) throws IOException {
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

}
