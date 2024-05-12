package com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reactions {
    private List<Datum_> data;
    private Paging paging;
    private Summary summary;
}
