package com.pocbuildings.otp_service.service;

import com.pocbuildings.otp_service.enums.NotificationChannel;
import com.pocbuildings.otp_service.notification.OtpNotificationStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NotificationFactory {

    private final Map<NotificationChannel, OtpNotificationStrategy> strategies;

    public NotificationFactory(List<OtpNotificationStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        OtpNotificationStrategy::getChannel,
                        Function.identity()
                ));
    }

    public OtpNotificationStrategy getStrategy(NotificationChannel channel) {
        OtpNotificationStrategy strategy = strategies.get(channel);

        if (strategy == null) {
            throw new IllegalArgumentException(
                    "Unsupported notification channel: " + channel);
        }

        return strategy;
    }
}