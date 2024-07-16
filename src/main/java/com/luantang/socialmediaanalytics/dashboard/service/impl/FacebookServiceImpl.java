package com.luantang.socialmediaanalytics.dashboard.service.impl;


import com.luantang.socialmediaanalytics.dashboard.constant.FacebookConstant;
import com.luantang.socialmediaanalytics.dashboard.dto.DashboardMessageDto;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.*;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_fans.PageFanResponse;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_impressions.PageImpressionResponse;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_post_engagements.PagePostEngagementResponse;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_views.PageViewResponse;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.user.account.Datum;
import com.luantang.socialmediaanalytics.dashboard.exceptions.AuthNotFoundException;
import com.luantang.socialmediaanalytics.dashboard.exceptions.InvalidAuthorizationCodeException;
import com.luantang.socialmediaanalytics.dashboard.exceptions.NullTokenException;
import com.luantang.socialmediaanalytics.dashboard.model.FacebookAuthDetails;
import com.luantang.socialmediaanalytics.dashboard.model.processed_data.*;
import com.luantang.socialmediaanalytics.dashboard.repository.FacebookAuthRepository;
import com.luantang.socialmediaanalytics.dashboard.service.FacebookDataProcessor;
import com.luantang.socialmediaanalytics.dashboard.service.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.luantang.socialmediaanalytics.dashboard.constant.FacebookConstant.FACEBOOK_AUTH_EXPIRATION;

@Service
public class FacebookServiceImpl implements FacebookService {
    private final FacebookAuthRepository facebookAuthRepository;
    private final RestTemplate restTemplate;
    private final FacebookDataProcessor facebookDataProcessor;


    @Autowired
    public FacebookServiceImpl(FacebookAuthRepository facebookAuthRepository, RestTemplate restTemplate, FacebookDataProcessor facebookDataProcessor) {
        this.facebookAuthRepository = facebookAuthRepository;
        this.restTemplate = restTemplate;
        this.facebookDataProcessor = facebookDataProcessor;
    }

    @Override
    public AuthUrlDto createAuthorizationUrl(String email) {
        String url = FacebookConstant.FACEBOOK_OAUTH_DIALOG_URL +
                "?client_id=" + FacebookConstant.FACEBOOK_APP_ID +
                "&scope=" + "pages_read_engagement," +
                            "read_insights," +
                            "pages_read_user_content," +
                            "pages_show_list," +
                            "pages_manage_engagement," +
                            "pages_manage_posts," +
                            "publish_video" +
                "&redirect_uri=" + "https://nair-social-media-analytics-production.up.railway.app/api/v1/user/facebook/access" +
                "?email=" + emailEncoder(email);
        return new AuthUrlDto(url);
    }

    @Override
    public DashboardMessageDto getAccess(String email, String authorizationCode) {
        String userAccessToken = getUserAccessToken(email, authorizationCode);
        String facebookUserId = getFacebookUserId(userAccessToken);
        com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.user.account.Root pages = getPageDetails(facebookUserId, userAccessToken);

        FacebookAuthDetails facebookAuthDetails = new FacebookAuthDetails();
        facebookAuthDetails.setFacebookUserId(facebookUserId);
        facebookAuthDetails.setUserAccessToken(userAccessToken);
        facebookAuthDetails.setPages(pages);
        facebookAuthDetails.setAppUserEmail(email);
        facebookAuthDetails.setExpiration(new Date(System.currentTimeMillis() + FACEBOOK_AUTH_EXPIRATION));

        facebookAuthRepository.save(facebookAuthDetails);

        return mapToMessageDto("Access successfully");
    }

    private String getUserAccessToken(String email, String authorizationCode) {
        try {
            String url = FacebookConstant.FACEBOOK_OAUTH_ACCESS_TOKEN_URL +
                    "?client_id=" + FacebookConstant.FACEBOOK_APP_ID +
                    "&client_secret=" + FacebookConstant.FACEBOOK_APP_SECRET +
                    "&code=" + authorizationCode +
                    "&redirect_uri=" + "https://nair-social-media-analytics-production.up.railway.app/api/v1/user/facebook/access" + "?email=" + emailEncoder(email);
            ResponseEntity<com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.user.token.Root> responseEntity =  restTemplate.getForEntity(url, com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.user.token.Root.class);

            return Objects.requireNonNull(responseEntity.getBody()).getAccess_token();
        }
        catch (NullPointerException ex) {
            //TODO change the exception
            throw new NullTokenException("Token is null");
        }
        catch (HttpClientErrorException ex) {
            throw new InvalidAuthorizationCodeException("Invalid verification code format or code has expired");
        }
    }

    private String getFacebookUserId(String userAccessToken) {
        try {
            String url = FacebookConstant.FACEBOOK_GRAPH_API_URL +
                    "/me?fields=" + "id" +
                    "&access_token=" + userAccessToken;
        ResponseEntity<com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.user.me.Root> responseEntity =  restTemplate.getForEntity(url, com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.user.me.Root.class);

            return Objects.requireNonNull(responseEntity.getBody()).getId();
        }
        catch (NullPointerException ex) {
            //TODO change the exception
            throw new NullTokenException("User id is null");
        }
    }

    private com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.user.account.Root getPageDetails(String facebookUserId, String userAccessToken) {
        try {
            String url = FacebookConstant.FACEBOOK_GRAPH_API_URL +
                    facebookUserId +
                    "/accounts?fields=access_token,category,category_list,name,id,tasks,picture" +
                    "&access_token=" + userAccessToken;
            ResponseEntity<com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.user.account.Root> responseEntity
                    = restTemplate.getForEntity(url, com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.user.account.Root.class);
            com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.user.account.Root userPageDetails = Objects.requireNonNull(responseEntity.getBody());

            return userPageDetails;
        }
        catch (NullPointerException ex) {
            //TODO change the excepion
            throw new NullTokenException("Pages details is null");
        }
    }

    @Override
    public FacebookAuthDetails saveAuthDetails(FacebookAuthDetails facebookAuthDetails) {
        try {
            //TODO check if user access token expired, or completely change the token
            return facebookAuthRepository.save(facebookAuthDetails);
        }
        catch (Exception e) {
            System.out.println(e);
            //TODO change the exception
            throw new NullTokenException("Can't save the authentication details");
        }
    }

    @Override
    public FacebookAuthDetails getAuthDetailsByAppUserEmail(String email) {
        FacebookAuthDetails authDetails = facebookAuthRepository.findByAppUserEmail(email)
                .orElseThrow(() -> new AuthNotFoundException("Authentication details with that associate email could not be found."));
        if(authDetails.getExpiration().before(new Date()))
            throw new AuthNotFoundException("Authentication details expired");
        else
            return authDetails;
    }

    @Override
    public PageDetailsDto getPageDetails(String email) {
        return mapToPageDetailsDto(getAuthDetailsByAppUserEmail(email).getPages());
    }


    @Override
    public List<PagePostPerformanceDto> getPostPerformances
            (String email ,String startDate, String endDate) {
        try {
            FacebookAuthDetails authDetails = getAuthDetailsByAppUserEmail(email);
            List<PagePostPerformanceDto> data = new ArrayList<>();
            for(Datum page : authDetails.getPages().getData()) {
                String url = FacebookConstant.FACEBOOK_GRAPH_API_URL +
                        page.getId() +
                        "/feed?fields=id," +
                            "message,created_time," +
                            "reactions.limit(100).summary(true)," +
                            "comments.limit(100) .summary(true)," +
                            "likes.limit(100).summary(true)" +
                        "&since=" + startDate +
                        "&until=" + endDate +
                        "&access_token=" + page.getAccess_token();
                ResponseEntity<com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.feed.Root> responseEntity
                        = restTemplate.getForEntity(url, com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.feed.Root.class);
                data.add(mapToPagePostPerformanceDto(page.getId(), facebookDataProcessor.matchPostPerformanceData(responseEntity.getBody())));
            }
            return data;
        }
        catch (NullPointerException ex) {
            //TODO change the exception
            throw new NullTokenException("Post performances is null");
        }
    }

    @Override
    public List<PageImpressionDto> getPageImpressions(String email, String startDate, String endDate) {
        try {
            FacebookAuthDetails authDetails = getAuthDetailsByAppUserEmail(email);

            List<PageImpressionDto> data = new ArrayList<>();

            for (Datum page : authDetails.getPages().getData()) {
                String url = FacebookConstant.FACEBOOK_GRAPH_API_URL +
                        page.getId() +
                        "/insights?metric=page_impressions" +
                        "&period=day" +
                        "&since=" + startDate +
                        "&until=" + endDate +
                        "&access_token=" + page.getAccess_token();

                ResponseEntity<PageImpressionResponse> responseEntity =
                        restTemplate.getForEntity(url, PageImpressionResponse.class);
                data.add(mapToPageImpressionsDto(page.getId(), facebookDataProcessor.matchPageImpressionsData(responseEntity.getBody())));
            }

            return data;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            throw new NullTokenException("Post performances is null");
        }
    }

    @Override
    public List<PagePostEngagementDto> getPagePostEngagements(String email, String startDate, String endDate) {
        try {
            FacebookAuthDetails authDetails = getAuthDetailsByAppUserEmail(email);

            List<PagePostEngagementDto> data = new ArrayList<>();

            for (Datum page : authDetails.getPages().getData()) {
                String url = FacebookConstant.FACEBOOK_GRAPH_API_URL +
                        page.getId() +
                        "/insights?metric=page_post_engagements" +
                        "&period=day" +
                        "&since=" + startDate +
                        "&until=" + endDate +
                        "&access_token=" + page.getAccess_token();

                ResponseEntity<PagePostEngagementResponse> responseEntity =
                        restTemplate.getForEntity(url, PagePostEngagementResponse.class);
                data.add(mapToPagePostEngagementDto(page.getId(), facebookDataProcessor.matchPagePostEngagementData(responseEntity.getBody())));
            }
            return data;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            throw new NullTokenException("Post performances is null");
        }
    }

    @Override
    public List<PageFanDto> getPageFans(String email, String startDate, String endDate) {
        try {
            FacebookAuthDetails authDetails = getAuthDetailsByAppUserEmail(email);

            List<PageFanDto> data = new ArrayList<>();

            for (Datum page : authDetails.getPages().getData()) {
                String url = FacebookConstant.FACEBOOK_GRAPH_API_URL +
                        page.getId() +
                        "/insights?metric=page_fans" +
                        "&period=day" +
                        "&since=" + startDate +
                        "&until=" + endDate +
                        "&access_token=" + page.getAccess_token();

                ResponseEntity<PageFanResponse> responseEntity =
                        restTemplate.getForEntity(url, PageFanResponse.class);
                data.add(mapToPageFanDto(page.getId(), facebookDataProcessor.matchPageFanData(responseEntity.getBody(), endDate)));
            }
            return data;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            throw new NullTokenException("Post performances is null");
        }
    }

    @Override
    public List<PageViewDto> getPageViews(String email, String startDate, String endDate) {
        try {
            FacebookAuthDetails authDetails = getAuthDetailsByAppUserEmail(email);

            List<PageViewDto> data = new ArrayList<>();

            for (Datum page : authDetails.getPages().getData()) {
                String url = FacebookConstant.FACEBOOK_GRAPH_API_URL +
                        page.getId() +
                        "/insights?metric=page_views_total" +
                        "&period=day" +
                        "&since=" + startDate +
                        "&until=" + endDate +
                        "&access_token=" + page.getAccess_token();

                ResponseEntity<PageViewResponse> responseEntity =
                        restTemplate.getForEntity(url, PageViewResponse.class);
                data.add(mapToPageViewDto(page.getId(), facebookDataProcessor.matchPageViewData(responseEntity.getBody())));
            }
            return data;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            throw new NullTokenException("Post performances is null");
        }
    }

    private PageDetailsDto mapToPageDetailsDto(com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.user.account.Root pages) {
        return new PageDetailsDto(pages.getData());
    }

    private DashboardMessageDto mapToMessageDto(String message) {
        DashboardMessageDto dashboardMessageDto = new DashboardMessageDto();
        dashboardMessageDto.setMessage(message);
        dashboardMessageDto.setTimestamp(new Date(System.currentTimeMillis()));
        return dashboardMessageDto;
    }

    private PagePostPerformanceDto mapToPagePostPerformanceDto(String pageId, List<PostPerformance> postPerformanceDataList) {
        PagePostPerformanceDto pagePostPerformanceDto = new PagePostPerformanceDto();
        pagePostPerformanceDto.setPageId(pageId);
        pagePostPerformanceDto.setPostPerformance(postPerformanceDataList);
        return pagePostPerformanceDto;
    }

    private PageImpressionDto mapToPageImpressionsDto(String pageId, PageImpression pageImpressions) {
        PageImpressionDto pageImpressionsDto = new PageImpressionDto();
        pageImpressionsDto.setPageId(pageId);
        pageImpressionsDto.setPageImpressions(pageImpressions);
        return pageImpressionsDto;
    }

    private PagePostEngagementDto mapToPagePostEngagementDto(String pageId, PagePostEngagement pagePostEngagement) {
        PagePostEngagementDto pagePostEngagementDto = new PagePostEngagementDto();
        pagePostEngagementDto.setPageId(pageId);
        pagePostEngagementDto.setPagePostEngagements(pagePostEngagement);
        return pagePostEngagementDto;
    }

    private PageFanDto mapToPageFanDto(String pageId, PageFan pageFan) {
        PageFanDto pageFanDto = new PageFanDto();
        pageFanDto.setPageId(pageId);
        pageFanDto.setPageFan(pageFan);
        return pageFanDto;
    }

    private PageViewDto mapToPageViewDto(String pageId, PageView pageView) {
        PageViewDto pageViewDto = new PageViewDto();
        pageViewDto.setPageId(pageId);
        pageViewDto.setPageView(pageView);
        return pageViewDto;
    }

    private static String emailEncoder(String emailAddress) {
        try {
            return URLEncoder.encode(emailAddress, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //TODO Handle email encoding failed
            System.err.println("Unsupported encoding: " + e.getMessage());
            return null;
        }
    }
}
