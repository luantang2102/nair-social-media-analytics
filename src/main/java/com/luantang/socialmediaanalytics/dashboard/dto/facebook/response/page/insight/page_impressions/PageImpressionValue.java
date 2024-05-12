package com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_impressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageImpressionValue {
    private Integer value;
    private String end_time;
}
