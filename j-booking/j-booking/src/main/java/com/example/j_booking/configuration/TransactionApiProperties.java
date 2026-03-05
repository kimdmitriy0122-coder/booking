package com.example.j_booking.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.transaction-api")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionApiProperties {
    String url;
}
