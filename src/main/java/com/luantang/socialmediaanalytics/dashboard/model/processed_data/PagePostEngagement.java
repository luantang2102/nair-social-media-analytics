package com.luantang.socialmediaanalytics.dashboard.model.processed_data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagePostEngagement {
    private int totalEngagement;
    private List<PagePostEngagementByDay> engagementByDayList;
}
