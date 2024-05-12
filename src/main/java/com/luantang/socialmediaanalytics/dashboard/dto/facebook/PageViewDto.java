package com.luantang.socialmediaanalytics.dashboard.dto.facebook;

import com.luantang.socialmediaanalytics.dashboard.model.processed_data.PageView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageViewDto {
    private String pageId;
    private PageView pageView;
}
