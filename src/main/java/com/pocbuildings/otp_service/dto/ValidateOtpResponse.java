package com.pocbuildings.otp_service.dto;

import com.pocbuildings.otp_service.enums.OtpStatus;

import java.util.UUID;




import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ValidateOtpResponse {

    private boolean valid;

    private String message;
}

