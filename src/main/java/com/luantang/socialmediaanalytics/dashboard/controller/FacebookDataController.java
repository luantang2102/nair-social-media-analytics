package com.luantang.socialmediaanalytics.dashboard.controller;

import com.luantang.socialmediaanalytics.dashboard.dto.facebook.*;
import com.luantang.socialmediaanalytics.dashboard.service.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user/facebook/pages")
public class FacebookDataController {

    private final FacebookService facebookService;

    @Autowired
    public FacebookDataController(FacebookService facebookService) {
        this.facebookService = facebookService;
    }

    @GetMapping("/details")
    public ResponseEntity<PageDetailsDto> getPageDetails() {
        return new ResponseEntity<>(facebookService.getPageDetails(SecurityContextHolder.getContext().getAuthentication().getName()), HttpStatus.OK);
    }

    @GetMapping("/page-post-performance")
    public ResponseEntity<List<PagePostPerformanceDto>> getPagePostPerformanceList(@RequestParam("since") String startDate, @RequestParam("until") String endDate) {
        return new ResponseEntity<>(facebookService.getPostPerformances(SecurityContextHolder.getContext().getAuthentication().getName(), startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/page-impressions")
    public ResponseEntity<List<PageImpressionDto>> getPageImpressions(@RequestParam("since") String startDate, @RequestParam("until") String endDate) {
        return new ResponseEntity<>(facebookService.getPageImpressions(SecurityContextHolder.getContext().getAuthentication().getName(), startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/page-post-engagements")
    public ResponseEntity<List<PagePostEngagementDto>> getPagePostEngagements(@RequestParam("since") String startDate, @RequestParam("until") String endDate) {
        return new ResponseEntity<>(facebookService.getPagePostEngagements(SecurityContextHolder.getContext().getAuthentication().getName(), startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/page-fans")
    public ResponseEntity<List<PageFanDto>> getPageFans(@RequestParam("since") String startDate, @RequestParam("until") String endDate) {
        return new ResponseEntity<>(facebookService.getPageFans(SecurityContextHolder.getContext().getAuthentication().getName(), startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/page-views_total")
    public ResponseEntity<List<PageViewDto>> getPageViewsTotal(@RequestParam("since") String startDate, @RequestParam("until") String endDate) {
        return new ResponseEntity<>(facebookService.getPageViews(SecurityContextHolder.getContext().getAuthentication().getName(), startDate, endDate), HttpStatus.OK);
    }
}
