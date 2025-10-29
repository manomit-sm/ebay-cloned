package com.ebay.backend.config;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.core.authentication.AzureIdentityAuthenticationProvider;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import com.microsoft.kiota.RequestAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
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

        final String[] allowedHosts = new String[] { "graph.microsoft.com" };
        final AzureIdentityAuthenticationProvider authProvider =
                new AzureIdentityAuthenticationProvider(credential, allowedHosts, "https://graph.microsoft.com/.default");
        return new GraphServiceClient(authProvider);
    }
}
