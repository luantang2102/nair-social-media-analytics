package com.luantang.socialmediaanalytics.dashboard.dto.edenai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TextGenerationDto {
    private String generatedText;
    private String provider;
}
