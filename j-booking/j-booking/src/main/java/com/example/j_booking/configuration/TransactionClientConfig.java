package com.example.j_booking.configuration;

import com.example.j_booking.handler.TransactionClientHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.HttpClientSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app.services.merchant-api")
public class TransactionClientConfig {
    String url;
    @Bean
    public RestClient transactionRestClient() {
        return RestClient
                .builder()
                .baseUrl(url)
                .requestFactory(clientHttpRequestFactory())
                .defaultStatusHandler(new TransactionClientHandler())
                .build();
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        var settings = HttpClientSettings
                .defaults()
                .withReadTimeout(Duration.ofSeconds(15))
                .withConnectTimeout(Duration.ofSeconds(20));

        return ClientHttpRequestFactoryBuilder.jdk().build(settings);
    }
}
