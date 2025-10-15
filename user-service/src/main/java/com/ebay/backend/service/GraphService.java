package com.ebay.backend.service;

import com.ebay.backend.dto.request.UserRegistrationRequest;
import com.microsoft.graph.models.PasswordProfile;
import com.microsoft.graph.models.User;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GraphService {

    private final GraphServiceClient graphServiceClient;

    public void createUser(UserRegistrationRequest userRegistrationRequest) {
        String displayName = userRegistrationRequest.firstName() + " " + userRegistrationRequest.lastName();

        // 2️⃣ Create Microsoft Graph user object
        User graphUser = new User();
        graphUser.setAccountEnabled(true);
        graphUser.setDisplayName(displayName);
        graphUser.setMailNickname(userRegistrationRequest.email().split("@")[0]);
        graphUser.setUserPrincipalName(userRegistrationRequest.email());

        PasswordProfile passwordProfile = new PasswordProfile();
        passwordProfile.setPassword(userRegistrationRequest.password());
        passwordProfile.setForceChangePasswordNextSignIn(false);
        graphUser.setPasswordProfile(passwordProfile);

        // 3️⃣ Call Microsoft Graph API
        User createdGraphUser = graphServiceClient.users()
                .post(graphUser);

        log.info("User Details {}", createdGraphUser);
    }
}
