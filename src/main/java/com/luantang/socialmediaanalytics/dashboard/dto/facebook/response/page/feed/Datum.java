package com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.feed;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Datum {
    private String id;
    private String message;
    private String created_time;
    private Reactions reactions;
    private Comments comments;
}