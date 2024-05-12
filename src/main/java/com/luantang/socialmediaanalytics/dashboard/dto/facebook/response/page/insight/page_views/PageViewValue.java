package com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageViewValue {
    private Integer value;
    private String end_time;
}
