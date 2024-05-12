package com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageViewResponse {
    private List<PageViewDatum> data;
}
