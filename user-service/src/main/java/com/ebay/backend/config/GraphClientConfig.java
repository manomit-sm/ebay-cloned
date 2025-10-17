package com.ebay.backend.config;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphClientConfig {

    @Value("${azure.activedirectory.tenant-id}")
    private String tenantId;

    @Value("${azure.activedirectory.client-id}")
    private String clientId;

    @Value("${azure.activedirectory.client-secret}")
    private String clientSecret;

    private static final String SCOPE = "https://graph.microsoft.com/.default";

    @Bean
    public GraphServiceClient graphServiceClient() {
        ClientSecretCredential credential = new ClientSecretCredentialBuilder()
                .tenantId(tenantId)
                .clientId(clientId)
                .clientSecret(clientSecret)

                .build();

        return new GraphServiceClient(credential, SCOPE);
    }
}
