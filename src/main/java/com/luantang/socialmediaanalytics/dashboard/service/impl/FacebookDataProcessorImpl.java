package com.luantang.socialmediaanalytics.dashboard.service.impl;

import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.feed.Datum;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.feed.Root;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_fans.PageFanDatum;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_fans.PageFanResponse;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_fans.PageFanValue;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_impressions.PageImpressionDatum;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_impressions.PageImpressionResponse;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_impressions.PageImpressionValue;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_post_engagements.PagePostEngagementDatum;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_post_engagements.PagePostEngagementResponse;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_post_engagements.PagePostEngagementValue;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_views.PageViewDatum;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_views.PageViewResponse;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.page.insight.page_views.PageViewValue;
import com.luantang.socialmediaanalytics.dashboard.model.processed_data.*;
import com.luantang.socialmediaanalytics.dashboard.service.FacebookDataProcessor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class FacebookDataProcessorImpl implements FacebookDataProcessor {

    //Match posts performance
    @Override
    public List<PostPerformance> matchPostPerformanceData
    (Root rawResponseData) {
        //TODO handle the cursors to next page
        try {
            List<PostPerformance> allPostPerformanceData = new ArrayList<>();
            for(Datum datum : rawResponseData.getData()) {
                PostPerformance postPerformanceData = new PostPerformance();
                postPerformanceData.setId(datum.getId());
                postPerformanceData.setCaption(datum.getMessage());
                try {
                    String inputDatePattern = "yyyy-MM-dd'T'HH:mm:ssZ";
                    String outputDatePattern = "yyyy-MM-dd HH:mm:ss";
                    SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputDatePattern);
                    SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputDatePattern);
                    postPerformanceData.setCreatedTime(outputDateFormat.format(inputDateFormat.parse(datum.getCreated_time())));
                } catch (ParseException e) {
                    //TODO Handle parse exception
                    throw new RuntimeException(e);
                }
                postPerformanceData.setTotalReaction(datum.getReactions().getSummary().getTotal_count());
                postPerformanceData.setTotalComment(datum.getComments().getSummary().getTotal_count());
                allPostPerformanceData.add(postPerformanceData);
            }
            //Sorting based on the sum of reactions and comments
            sortPostPerformance(allPostPerformanceData);
            return allPostPerformanceData;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            //TODO handle match failed
            throw new RuntimeException();
        }
    }

    private void sortPostPerformance(List<PostPerformance> postPerformanceList) {
        for (int i = 0; i < postPerformanceList.size(); i++) {
            for (int j = i + 1; j < postPerformanceList.size(); j++) {
                int sum1 = postPerformanceList.get(i).getTotalReaction() + postPerformanceList.get(i).getTotalComment();
                int sum2 = postPerformanceList.get(j).getTotalReaction() + postPerformanceList.get(j).getTotalComment();
                if (sum1 < sum2) {
                    PostPerformance temp = postPerformanceList.get(i);
                    postPerformanceList.set(i, postPerformanceList.get(j));
                    postPerformanceList.set(j, temp);
                }
            }
        }
    }

    @Override
    public PageImpression matchPageImpressionsData
            (PageImpressionResponse rawResponseData) {
        int totalImpression = 0;
        List<PageImpressionByDay> impressionList = new ArrayList<>();
        for (PageImpressionDatum
                datum : rawResponseData.getData()) {
            for (PageImpressionValue value : datum.getValues()) {
                totalImpression += value.getValue();
                try {
                    impressionList.add(new PageImpressionByDay(new SimpleDateFormat("MM-dd-yyyy").format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(value.getEnd_time())), value.getValue()));
                } catch (ParseException e) {
                    //TODO handle parse
                    throw new RuntimeException(e);
                }
            }
        }
        PageImpression pageImpressions = new PageImpression();
        pageImpressions.setTotalImpression(totalImpression);
        pageImpressions.setImpressionsByDayList(impressionList);
        return pageImpressions;
    }

    @Override
    public PagePostEngagement matchPagePostEngagementData(PagePostEngagementResponse rawResponseData) {
        int totalEngagement = 0;
        List<PagePostEngagementByDay> engagementByDayList = new ArrayList<>();
        for (PagePostEngagementDatum
                datum : rawResponseData.getData()) {
            for (PagePostEngagementValue
                    value : datum.getValues()) {
                totalEngagement += value.getValue();
                try {
                    engagementByDayList.add(new PagePostEngagementByDay(new SimpleDateFormat("MM-dd-yyyy").format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(value.getEnd_time())), value.getValue()));
                } catch (ParseException e) {
                    //TODO handle parse
                    throw new RuntimeException(e);
                }
            }
        }
        PagePostEngagement pagePostEngagement = new PagePostEngagement();
        pagePostEngagement.setTotalEngagement(totalEngagement);
        pagePostEngagement.setEngagementByDayList(engagementByDayList);
        return pagePostEngagement;
    }

    @Override
    public PageFan matchPageFanData(PageFanResponse rawResponseData, String endDate) {
        int totalFan = 0;
        List<PageTotalFanByDay> fanByDayList = new ArrayList<>();
        for (PageFanDatum datum : rawResponseData.getData()) {
            for (PageFanValue value : datum.getValues()) {
                try {
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(value.getEnd_time()));
                    if(date.equals(endDate)) {
                        totalFan = value.getValue();
                    }
                    fanByDayList.add(new PageTotalFanByDay(new SimpleDateFormat("MM-dd-yyyy").format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(value.getEnd_time())), value.getValue()));
                }
                catch (ParseException e) {
                    //TODO handle parse
                    throw new RuntimeException(e);
                }
            }
        }
        PageFan pageFan = new PageFan();
        pageFan.setTotalFan(totalFan);
        pageFan.setTotalFanByDayList(fanByDayList);
        return pageFan;
    }

    @Override
    public PageView matchPageViewData(PageViewResponse rawResponseData) {
        int totalView = 0;
        List<PageViewByDay> viewByDayList = new ArrayList<>();
        for (PageViewDatum
                datum : rawResponseData.getData()) {
            for (PageViewValue
                    value : datum.getValues()) {
                totalView += value.getValue();
                try {
                    viewByDayList.add(new PageViewByDay(new SimpleDateFormat("MM-dd-yyyy").format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(value.getEnd_time())), value.getValue()));
                } catch (ParseException e) {
                    //TODO handle parse
                    throw new RuntimeException(e);
                }
            }
        }
        PageView pageView = new PageView();
        pageView.setTotalView(totalView);
        pageView.setViewByDayList(viewByDayList);
        return pageView;
    }
}
