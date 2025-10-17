package com.ebay.backend.controller;

import com.ebay.backend.dto.request.UserRegistrationRequest;
import com.ebay.backend.dto.response.UserRegistrationResponse;
import com.ebay.backend.service.GraphService;
import com.ebay.backend.util.ValidationErrorMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<?> register(
            @Valid @RequestBody UserRegistrationRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorMapper.fromBindingErrors(bindingResult));
        }
        UserRegistrationResponse graphServiceUser = graphService.createUser(request);
        return ResponseEntity.ok(graphServiceUser);
    }
}
