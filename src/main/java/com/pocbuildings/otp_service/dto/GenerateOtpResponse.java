package com.pocbuildings.otp_service.dto;

import com.pocbuildings.otp_service.enums.OtpStatus;

import java.time.Instant;
import java.util.UUID;



import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class GenerateOtpResponse {

    private UUID otpRequestId;

    private OtpStatus status;

    private Instant expiresAt;

    private String message;
}

