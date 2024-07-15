package com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_fans.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TextGenerationRequest  {
    private String providers;
    private String text;
    private Integer max_tokens;
    private Double temperature;
}
