package com.luantang.socialmediaanalytics.dashboard.controller;

import com.luantang.socialmediaanalytics.dashboard.dto.DashboardMessageDto;
import com.luantang.socialmediaanalytics.dashboard.dto.facebook.AuthUrlDto;
import com.luantang.socialmediaanalytics.dashboard.service.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user/facebook")
public class FacebookAuthController {
    private final FacebookService facebookService;

    @Autowired
    public FacebookAuthController(FacebookService facebookService) {
        this.facebookService = facebookService;
    }

    @GetMapping("/access")
    public ResponseEntity<DashboardMessageDto> getAccess(@RequestParam("email") String email, @RequestParam("code") String authorizationCode) {
        return new ResponseEntity<>(facebookService.getAccess(email, authorizationCode), HttpStatus.OK);
    }

    @GetMapping("/authorize-url")
    public ResponseEntity<AuthUrlDto> getAuthorizeUrl() {
        return new ResponseEntity<>(facebookService.createAuthorizationUrl(SecurityContextHolder.getContext().getAuthentication().getName()), HttpStatus.CREATED);
    }

}
