package com.ebay.backend.controller;

import com.ebay.backend.dto.request.UserRegistrationRequest;
import com.ebay.backend.service.GraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final GraphService graphService;

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody UserRegistrationRequest request) {
        graphService.createUser(request);
        return ResponseEntity.ok("User registered successfully.");
    }
}
