package com.pocbuildings.otp_service.service;



import com.pocbuildings.otp_service.enums.NotificationChannel;

public interface OtpNotificationService {

    void sendOtp(
            String recipientReference,
            String otp,
            NotificationChannel channel);
}
