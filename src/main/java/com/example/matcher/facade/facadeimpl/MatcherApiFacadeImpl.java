package com.example.matcher.facade.facadeimpl;

import com.example.matcher.facade.facadecore.MatcherApiFacade;
import com.example.matcher.fileProcessor.CvsFileProcessor;
import com.example.matcher.fileProcessor.CvsFileProcessorImpl;
import com.example.matcher.model.response.MatchingResponse;
import com.example.matcher.service.MatchingService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;


@Component
public class MatcherApiFacadeImpl implements MatcherApiFacade {
    private final MatchingService matchingService;

    public MatcherApiFacadeImpl(final MatchingService matchingService) {
        this.matchingService = matchingService;
    }
    @Override
    public MatchingResponse uploadAndProcessFile(MultipartFile file) {
        return matchingService.matchEmployeesFromFile(file);
    }
}
