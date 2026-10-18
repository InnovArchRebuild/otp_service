package com.pocbuildings.otp_service.notification;

import com.pocbuildings.otp_service.enums.NotificationChannel;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class SmsNotificationStrategy implements OtpNotificationStrategy {

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.SMS;
    }

    @Override
    public void sendOtp(String recipientReference, String otp) {
        System.out.println("===== LOCAL SMS NOTIFICATION =====");
        System.out.println("Mobile: " + recipientReference);
        System.out.println("OTP: " + otp);
        System.out.println("==================================");
    }
}