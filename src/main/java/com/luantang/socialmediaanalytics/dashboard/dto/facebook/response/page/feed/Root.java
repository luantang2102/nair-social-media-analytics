package com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.feed;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Root {
    private List<Datum> data;
    private Paging__ paging;
}