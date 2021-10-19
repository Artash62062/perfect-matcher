package com.example.matcher.fileProcessor;


import com.example.matcher.fileProcessor.exceptions.EmptyFileException;
import com.example.matcher.fileProcessor.exceptions.UnreadableFileException;
import com.example.matcher.model.Employee;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

@Component
public class CvsFileProcessorImpl implements CvsFileProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CvsFileProcessorImpl.class);
    private static final String CVS_CONTENT_TYPE = "text/csv";

    public List<Employee> getEmployeesFromFile(final MultipartFile file) {
        LOGGER.info("Start File Processing for file with name - {}", file.getOriginalFilename());
        if (!Objects.equals(file.getContentType(), CVS_CONTENT_TYPE)) {
            throw new UnreadableFileException("Unexpected Content Type of File");
        }
        try (final Reader reader = new InputStreamReader(file.getInputStream())) {
            final CsvToBean<Employee> bean =
                    new CsvToBeanBuilder<Employee>(reader)
                            .withType(Employee.class)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();
            final List<Employee> employees = bean.parse();
            if (employees == null || employees.isEmpty()) {
                throw new EmptyFileException(String.format("file with name %s not contains expected employees", file.getOriginalFilename()));
            } else {
                return employees;
            }
        } catch (IOException exception) {
            LOGGER.error("Processing of file with name - {} is imposable ", file.getOriginalFilename());
            exception.printStackTrace();
            throw new UnreadableFileException(String.format("Processing of file with name - %s is imposable ", file.getOriginalFilename()));
        }
    }
}
