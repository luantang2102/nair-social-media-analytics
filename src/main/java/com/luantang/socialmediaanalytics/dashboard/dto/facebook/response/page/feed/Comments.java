package com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.feed;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Comments {
    private List<Datum__> data;
    private Paging_ paging;
    private Summary_ summary;
}
