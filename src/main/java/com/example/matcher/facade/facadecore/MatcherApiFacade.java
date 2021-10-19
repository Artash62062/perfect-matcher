package com.example.matcher.facade.facadecore;

import com.example.matcher.model.response.MatchingResponse;
import org.springframework.web.multipart.MultipartFile;

import java.nio.channels.MulticastChannel;

public interface MatcherApiFacade {

    MatchingResponse uploadAndProcessFile(MultipartFile file);

}
