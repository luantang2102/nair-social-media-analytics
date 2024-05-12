package com.luantang.socialmediaanalytics.dashboard.controller;

import com.luantang.socialmediaanalytics.dashboard.dto.InspireDto;
import com.luantang.socialmediaanalytics.dashboard.dto.edenai.TextGenerationDto;
import com.luantang.socialmediaanalytics.dashboard.service.EdenAIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user/inspiration")
public class InspirationController {
    private final EdenAIService edenAIService;

    public InspirationController(EdenAIService edenAIService) {
        this.edenAIService = edenAIService;
    }

    @PostMapping("/caption")
    public ResponseEntity<TextGenerationDto> getInspireCaption(@RequestBody InspireDto inspireDto) {
        return new ResponseEntity<>(edenAIService.getCaption(SecurityContextHolder.getContext().getAuthentication().getName(), inspireDto.getText(), inspireDto.getTone()), HttpStatus.OK);
    }
}
