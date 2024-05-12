package com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_impressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageImpressionResponse {
    private List<PageImpressionDatum> data;
}
