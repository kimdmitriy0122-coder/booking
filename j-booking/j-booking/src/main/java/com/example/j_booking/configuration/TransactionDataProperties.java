package com.example.j_booking.configuration;

import com.example.j_booking.constants.Currency;
import com.example.j_booking.constants.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@ConfigurationProperties(prefix = "app.transaction-data")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDataProperties {
    TransactionType type;
    Long amount;
    Currency currency;
    UUID merchantId;
    String senderName;
    String senderToken;
    String receiverName;
    String receiverToken;
}
