package com.luantang.socialmediaanalytics.dashboard.model.processed_data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostPerformance {
    private String id;
    private String caption;
    private String createdTime;
    private int totalReaction;
    private int totalComment;
}
