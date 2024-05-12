package com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_fans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageFanResponse {
    private List<PageFanDatum> data;
}
