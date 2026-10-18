package com.pocbuildings.otp_service.service;



import com.pocbuildings.otp_service.enums.NotificationChannel;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class ConsoleOtpNotificationService
        implements OtpNotificationService {

    @Override
    public void sendOtp(
            String recipientReference,
            String otp,
            NotificationChannel channel) {

        System.out.println("===== LOCAL OTP NOTIFICATION =====");
        System.out.println("Channel: " + channel);
        System.out.println("Recipient: " + recipientReference);
        System.out.println("OTP: " + otp);
        System.out.println("==================================");
    }
}
