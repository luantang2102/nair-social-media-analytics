package com.luantang.socialmediaanalytics.dashboard.service;

import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_fans.PageFanResponse;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_impressions.PageImpressionResponse;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_post_engagements.PagePostEngagementResponse;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_views.PageViewResponse;
import com.luantang.socialmediaanalytics.dashboard.model.processed_data.*;

import java.util.List;

public interface FacebookDataProcessor {

    List<PostPerformance> matchPostPerformanceData(com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.feed.Root responseEntity);

    PageImpression matchPageImpressionsData
            (PageImpressionResponse rawResponseData);

    PagePostEngagement matchPagePostEngagementData(PagePostEngagementResponse body);

    PageFan matchPageFanData(PageFanResponse rawResponseData, String endDate);

    PageView matchPageViewData(PageViewResponse rawResponseData);
}
