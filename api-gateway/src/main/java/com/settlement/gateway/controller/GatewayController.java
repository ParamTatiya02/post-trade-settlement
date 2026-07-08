package com.settlement.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GatewayController {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${services.intake-url}") private String intakeUrl;
    @Value("${services.settlement-url}") private String settlementUrl;

    @RequestMapping("/api/**")
    public ResponseEntity<String> route(HttpServletRequest req, @RequestBody(required = false) String body) {
        String path = req.getRequestURI();
        String base;
        if(path.startsWith("/api/trades")) base = intakeUrl;
        else if (path.startsWith("/api/settlements")) base = settlementUrl;
        else return ResponseEntity.status(404).body("Not Found");

        String qs = req.getQueryString();
        String url = base + path + (qs != null ? "?" + qs : "");

        HttpHeaders h = new HttpHeaders();
        String auth = req.getHeader("Authorization");
        if (auth != null) h.set("Authorization", auth);
        h.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.exchange(url, HttpMethod.valueOf(req.getMethod()),
                new HttpEntity<>(body, h), String.class);
    }
}
