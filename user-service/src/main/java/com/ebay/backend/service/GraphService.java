package com.ebay.backend.service;

import com.ebay.backend.dto.request.UserRegistrationRequest;
import com.ebay.backend.dto.response.UserRegistrationResponse;
import com.ebay.backend.mapper.UserMapper;
import com.ebay.backend.repository.UserRepository;
import com.ebay.backend.util.UserUtil;
import com.microsoft.graph.models.*;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import com.microsoft.graph.users.item.sendmail.SendMailPostRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class GraphService {

    private final GraphServiceClient graphServiceClient;

    private final String azIssuer;

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final UserUtil userUtil;

    public GraphService(
            GraphServiceClient graphServiceClient,
            @Value("${azure.activedirectory.issuer}") final String azIssuer,
            UserMapper userMapper,
            UserRepository userRepository,
            UserUtil userUtil
    ) {
        this.graphServiceClient = graphServiceClient;
        this.azIssuer = azIssuer;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.userUtil = userUtil;
    }

    @Transactional
    public UserRegistrationResponse createUser(UserRegistrationRequest userRegistrationRequest) throws IOException {
        String displayName = userRegistrationRequest.firstName() + " " + userRegistrationRequest.lastName();

        // 2️⃣ Create Microsoft Graph user object
        var mailNickName = userRegistrationRequest.email().split("@")[0];
        User graphUser = createAzureGraphUser(userRegistrationRequest, displayName, mailNickName);

        // 3️⃣ Call Microsoft Graph API
        User createdGraphUser = graphServiceClient.users()
                .post(graphUser);

        assert createdGraphUser != null;
        var newUser = userMapper.toUserEntity(userRegistrationRequest, createdGraphUser.getId());

        userUtil.sendVerificationEmail(userRegistrationRequest.email(), userUtil.getEmailVerificationCode());

        var savedUser = userRepository.save(newUser);

        return userMapper.toUserResponse(savedUser);
    }

    @NotNull
    private User createAzureGraphUser(UserRegistrationRequest userRegistrationRequest, String displayName, String mailNickName) {
        User graphUser = new User();
        graphUser.setAccountEnabled(false);
        graphUser.setDisplayName(displayName);
        graphUser.setMailNickname(mailNickName);
        graphUser.setUserPrincipalName(String.format("%s@%s", mailNickName, azIssuer));

        PasswordProfile passwordProfile = new PasswordProfile();
        passwordProfile.setPassword(userRegistrationRequest.password());
        passwordProfile.setForceChangePasswordNextSignIn(false);
        graphUser.setPasswordProfile(passwordProfile);
        graphUser.setMail(userRegistrationRequest.email());
        return graphUser;
    }
}
