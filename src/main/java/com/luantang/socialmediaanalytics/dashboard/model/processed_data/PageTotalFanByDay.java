package com.luantang.socialmediaanalytics.dashboard.model.processed_data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageTotalFanByDay {
    private String date;
    private int value;
}
