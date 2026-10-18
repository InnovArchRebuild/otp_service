package com.pocbuildings.otp_service.notification;



import com.pocbuildings.otp_service.enums.NotificationChannel;

public interface OtpNotificationStrategy {

    NotificationChannel getChannel();

    void sendOtp(String recipientReference, String otp);
}

