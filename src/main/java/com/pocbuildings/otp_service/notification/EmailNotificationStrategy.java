package com.pocbuildings.otp_service.notification;

import com.pocbuildings.otp_service.enums.NotificationChannel;
import com.pocbuildings.otp_service.notification.OtpNotificationStrategy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class EmailNotificationStrategy implements OtpNotificationStrategy {

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.EMAIL;
    }

    @Override
    public void sendOtp(String recipientReference, String otp) {
        System.out.println("===== LOCAL EMAIL NOTIFICATION =====");
        System.out.println("Email: " + recipientReference);
        System.out.println("OTP: " + otp);
        System.out.println("====================================");
    }
}

