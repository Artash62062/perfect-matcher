package com.example.matcher.service;

import com.example.matcher.model.response.MatchingResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MatchingService {
     MatchingResponse matchEmployeesFromFile (MultipartFile file);
}
