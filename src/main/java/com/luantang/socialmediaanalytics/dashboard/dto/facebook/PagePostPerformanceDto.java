package com.luantang.socialmediaanalytics.dashboard.dto.facebook;

import com.luantang.socialmediaanalytics.dashboard.model.processed_data.PostPerformance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagePostPerformanceDto {
    private String pageId;
    private List<PostPerformance>  postPerformance;
}
