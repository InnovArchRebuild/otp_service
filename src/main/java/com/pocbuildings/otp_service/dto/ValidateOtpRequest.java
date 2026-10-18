package com.pocbuildings.otp_service.dto;

import java.util.UUID;




import com.pocbuildings.otp_service.enums.OtpPurpose;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ValidateOtpRequest {

    @NotNull
    private UUID otpRequestId;

    @NotBlank
    @Size(min = 6, max = 6)
    private String otp;

    @NotNull
    private OtpPurpose purpose;

    @NotBlank
    @Size(max = 100)
    private String referenceId;
}

