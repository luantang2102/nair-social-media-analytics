package com.luantang.socialmediaanalytics.dashboard.service.impl;

import com.luantang.socialmediaanalytics.authentication.model.UserEntity;
import com.luantang.socialmediaanalytics.authentication.repository.service.UserService;
import com.luantang.socialmediaanalytics.dashboard.constant.EdenAIConstant;
import com.luantang.socialmediaanalytics.dashboard.dto.edenai.TextGenerationDto;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_fans.request.TextGenerationRequest;
import com.luantang.socialmediaanalytics.dashboard.dto.edenai.response.TextGenerationResponse;
import com.luantang.socialmediaanalytics.dashboard.service.EdenAIService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class EdenAIServiceImpl implements EdenAIService {
    private final RestTemplate restTemplate;
    private final UserService userService;

    public EdenAIServiceImpl(RestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    @Override
    public TextGenerationDto getCaption(String email, String text, String tone) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + EdenAIConstant.API_KEY);

            String edenAITextGeneratorUrl = "https://api.edenai.run/v2/text/generation";

            TextGenerationRequest captionRequest = new TextGenerationRequest();
            captionRequest.setProviders("amazon");
            captionRequest.setText("Write a facebook page post caption about: \"" + text + "\" with tone " + tone + " and having hashtag");
            captionRequest.setTemperature(0.2);
            captionRequest.setMax_tokens(250);

            HttpEntity<TextGenerationRequest> request =
                    new HttpEntity<>(captionRequest, headers);

            ResponseEntity<TextGenerationResponse> responseEntity = restTemplate.
                    postForEntity(edenAITextGeneratorUrl, request, TextGenerationResponse.class);

            UserEntity updateUser = userService.getUserByEmail(email);
            updateUser.setInspireTokens(updateUser.getInspireTokens() - 1);
            userService.updateUserByEmail(email, updateUser);

            return new TextGenerationDto(responseEntity.getBody().getAmazon().getGenerated_text(), responseEntity.getBody().getAmazon().getClass().getSimpleName());
        }
        catch (HttpClientErrorException ex) {
            //TODO handle the exception
            ex.printStackTrace();
            return null;
        }
    }

}
