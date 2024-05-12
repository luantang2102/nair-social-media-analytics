package com.luantang.socialmediaanalytics.dashboard.dto.edenai.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TextGenerationRequest  {
    private String providers;
    private String text;
    private Integer max_tokens;
    private Integer temperature;
    private String fallback_providers;
}
