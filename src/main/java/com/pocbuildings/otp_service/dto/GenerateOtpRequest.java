package com.pocbuildings.otp_service.dto;

import com.pocbuildings.otp_service.enums.NotificationChannel;
import com.pocbuildings.otp_service.enums.OtpPurpose;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class GenerateOtpRequest {

    @NotBlank
    @Size(max = 64)
    private String customerId;

    @NotNull
    private OtpPurpose purpose;

    @NotBlank
    @Size(max = 100)
    private String referenceId;

    @NotBlank
    @Size(max = 150)
    private String recipientReference;

    @NotNull
    private NotificationChannel channel;
}