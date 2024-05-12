package com.luantang.socialmediaanalytics.dashboard.service;

import com.luantang.socialmediaanalytics.dashboard.dto.edenai.TextGenerationDto;

public interface EdenAIService {

    TextGenerationDto getCaption(String email, String text, String tone);
}
