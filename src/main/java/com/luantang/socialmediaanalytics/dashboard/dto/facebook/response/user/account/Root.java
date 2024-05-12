package com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.user.account;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Root {
    private List<Datum> data = new ArrayList<Datum>();
    private Paging paging;
}
