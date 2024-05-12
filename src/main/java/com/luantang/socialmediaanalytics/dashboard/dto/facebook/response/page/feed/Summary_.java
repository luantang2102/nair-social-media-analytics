package com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Summary_ {
    private String order;
    private Integer total_count;
    private Boolean can_comment;
}
