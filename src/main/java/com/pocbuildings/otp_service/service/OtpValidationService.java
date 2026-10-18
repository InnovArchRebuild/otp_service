package com.pocbuildings.otp_service.service;


import com.pocbuildings.otp_service.dto.ValidateOtpRequest;
import com.pocbuildings.otp_service.dto.ValidateOtpResponse;
import com.pocbuildings.otp_service.entity.OtpRecord;
import com.pocbuildings.otp_service.enums.OtpStatus;
import com.pocbuildings.otp_service.repository.OtpRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OtpValidationService {

    private static final int MAX_ATTEMPTS = 3;

    private final OtpRepository otpRepository;
    private final OtpCryptographyService otpCryptographyService;

    public OtpValidationService(
            OtpRepository otpRepository,
            OtpCryptographyService otpCryptographyService) {
        this.otpRepository = otpRepository;
        this.otpCryptographyService = otpCryptographyService;
    }

    @Transactional
    public ValidateOtpResponse validateOtp(ValidateOtpRequest request) {

        OtpRecord otpRecord = otpRepository
                .findByIdForUpdate(request.getOtpRequestId())
                .orElse(null);

        if (otpRecord == null) {
            return buildResponse(false, "OTP request not found");
        }

        if (otpRecord.getStatus() != OtpStatus.ACTIVE) {
            return buildResponse(false, "OTP is no longer active");
        }

        if (Instant.now().isAfter(otpRecord.getExpiresAt())) {
            return buildResponse(false, "OTP has expired");
        }

        if (otpRecord.getAttemptCount() >= MAX_ATTEMPTS) {
            otpRecord.setStatus(OtpStatus.LOCKED);
            return buildResponse(false, "Maximum OTP attempts exceeded");
        }

        if (otpRecord.getPurpose() != request.getPurpose()
                || !otpRecord.getReferenceId()
                .equals(request.getReferenceId())) {
            return buildResponse(false,
                    "OTP does not match the requested purpose or reference");
        }

        boolean valid = otpCryptographyService.verifyOtp(
                request.getOtp(),
                otpRecord.getOtpVerifier()
        );

        if (valid) {
            otpRecord.setStatus(OtpStatus.VERIFIED);
            return buildResponse(true, "OTP validated successfully");
        }

        int attempts = otpRecord.getAttemptCount() + 1;
        otpRecord.setAttemptCount(attempts);

        if (attempts >= MAX_ATTEMPTS) {
            otpRecord.setStatus(OtpStatus.LOCKED);
            return buildResponse(false,
                    "Maximum OTP attempts exceeded");
        }

        return buildResponse(false, "Invalid OTP");
    }

    private ValidateOtpResponse buildResponse(
            boolean valid, String message) {
        ValidateOtpResponse response = new ValidateOtpResponse();
        response.setValid(valid);
        response.setMessage(message);
        return response;
    }
}


