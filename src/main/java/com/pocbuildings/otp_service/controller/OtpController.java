package com.pocbuildings.otp_service.controller;



import com.pocbuildings.otp_service.dto.GenerateOtpRequest;
import com.pocbuildings.otp_service.dto.GenerateOtpResponse;
import com.pocbuildings.otp_service.dto.ValidateOtpRequest;
import com.pocbuildings.otp_service.dto.ValidateOtpResponse;
import com.pocbuildings.otp_service.service.OtpService;
import jakarta.validation.Valid;

import com.pocbuildings.otp_service.dto.ValidateOtpRequest;
import com.pocbuildings.otp_service.dto.ValidateOtpResponse;
import com.pocbuildings.otp_service.service.OtpValidationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/otps")
public class OtpController {

    private final OtpService otpService;
    private final OtpValidationService otpValidationService;

    public OtpController(
            OtpService otpService,
            OtpValidationService otpValidationService) {

        this.otpService = otpService;
        this.otpValidationService = otpValidationService;
    }

    @PostMapping
    public ResponseEntity<GenerateOtpResponse> generateOtp(
            @Valid @RequestBody GenerateOtpRequest request) {

        GenerateOtpResponse response =
                otpService.generateOtp(request);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(response);
    }




    @PostMapping("/validate")
    public ResponseEntity<ValidateOtpResponse> validateOtp(
            @Valid @RequestBody ValidateOtpRequest request) {

        ValidateOtpResponse response =
                otpValidationService.validateOtp(request);

        return ResponseEntity.ok(response);
    }

}

