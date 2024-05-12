package com.luantang.socialmediaanalytics.dashboard.service;

import com.luantang.socialmediaanalytics.dashboard.dto.DashboardMessageDto;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.*;
import com.luantang.socialmediaanalytics.dashboard.model.FacebookAuthDetails;

import java.util.List;

public interface FacebookService {
    AuthUrlDto createAuthorizationUrl(String email);

    DashboardMessageDto getAccess(String email, String authorizationCode);

    List<PagePostPerformanceDto> getPostPerformances(String email, String startDate, String endDate);

    List<PageImpressionDto> getPageImpressions(String email, String startDate, String endDate);

    List<PagePostEngagementDto> getPagePostEngagements(String email, String startDate, String endDate);

    List<PageFanDto> getPageFans(String email, String startDate, String endDate);

    FacebookAuthDetails saveAuthDetails(FacebookAuthDetails facebookAuthDetails);

    FacebookAuthDetails getAuthDetailsByAppUserEmail(String email);

    PageDetailsDto getPageDetails(String email);

    List<PageViewDto> getPageViews(String email, String startDate, String endDate);
}
