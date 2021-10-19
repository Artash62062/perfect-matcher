package com.example.matcher.fileProcessor;

import com.example.matcher.model.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CvsFileProcessor {
    List<Employee> getEmployeesFromFile(final MultipartFile file);
}
