package com.pocbuildings.otp_service.service;


import com.pocbuildings.otp_service.dto.GenerateOtpRequest;
import com.pocbuildings.otp_service.dto.GenerateOtpResponse;
import com.pocbuildings.otp_service.entity.OtpRecord;
import com.pocbuildings.otp_service.enums.OtpStatus;
import com.pocbuildings.otp_service.notification.OtpNotificationStrategy;
import com.pocbuildings.otp_service.repository.OtpRepository;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class OtpService {

    private final OtpRepository otpRepository;
    private final OtpCryptographyService otpCryptographyService;
    private final NotificationFactory notificationFactory;

    public OtpService(
            OtpRepository otpRepository,
            OtpCryptographyService otpCryptographyService,
            NotificationFactory notificationFactory ) {
        this.otpRepository = otpRepository;
        this.otpCryptographyService = otpCryptographyService;
        this.notificationFactory = notificationFactory;
    }


    @Transactional
    public GenerateOtpResponse generateOtp(
            GenerateOtpRequest request) {

        // 1. Generate the six-digit OTP.
        String otp = otpCryptographyService.generateOtp();


// Temporary local testing only
        System.out.println("DEV ONLY - Generated OTP: " + otp);



        // 2. Protect the OTP before storing it.
        String otpVerifier = otpCryptographyService.createVerifier(otp);

        // 3. Create the database entity.
        Instant now = Instant.now();

        OtpRecord record = new OtpRecord();
        record.setOtpId(UUID.randomUUID());
        record.setCustomerId(request.getCustomerId());
        record.setPurpose(request.getPurpose());
        record.setReferenceId(request.getReferenceId());
        record.setRecipientReference(
                request.getRecipientReference());
        record.setChannel(request.getChannel());
        record.setOtpVerifier(otpVerifier);
        record.setExpiresAt(now.plus(5, ChronoUnit.MINUTES));
        //record.setExpiresAt(now.plusSeconds(15));
        record.setAttemptCount(0);
        record.setStatus(OtpStatus.ACTIVE);
        record.setCreatedOn(now);

        // 4. Save the record in Oracle.
        OtpRecord savedRecord = otpRepository.save(record);
        OtpNotificationStrategy strategy =
                notificationFactory.getStrategy(request.getChannel());

        strategy.sendOtp(
                request.getRecipientReference(),
                otp
        );

        // 5. Return metadata, not the actual OTP.
        GenerateOtpResponse response = new GenerateOtpResponse();
        response.setOtpRequestId(savedRecord.getOtpId());
        response.setStatus(savedRecord.getStatus());
        response.setExpiresAt(savedRecord.getExpiresAt());
        response.setMessage("OTP request created successfully");

        return response;
    }
}

