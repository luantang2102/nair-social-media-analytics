package com.luantang.socialmediaanalytics.dashboard.dto.facebook;

import com.luantang.socialmediaanalytics.dashboard.model.processed_data.PageFan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageFanDto {
    private String pageId;
    private PageFan pageFan;
}
