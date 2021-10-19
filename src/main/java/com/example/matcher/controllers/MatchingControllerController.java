package com.example.matcher.controllers;


import com.example.matcher.facade.facadecore.MatcherApiFacade;
import com.example.matcher.model.response.MatchingResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MatchingControllerController {

    MatcherApiFacade matcherApiFacade;

    public MatchingControllerController(final MatcherApiFacade matcherApiFacade) {
        this.matcherApiFacade = matcherApiFacade;
    }

    @GetMapping("/")
    public String showHomePage(Model model,RedirectAttributes redirectAttributes) {
        model.addAttribute("message", redirectAttributes.getFlashAttributes());
        return "greeting";
    }


    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   Model model) {
        MatchingResponse response = matcherApiFacade.uploadAndProcessFile(file);
        model.addAttribute("message",
                "The Best Average Percentage of Mapping is" + response.getAveragePercentage() + "!");
        return "responce";
    }


}