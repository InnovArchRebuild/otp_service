package com.pocbuildings.otp_service.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.Locale;

@Component
public class OtpCryptographyService {

    private final SecureRandom secureRandom = new SecureRandom();
    private final byte[] hmacSecret;

    public OtpCryptographyService(
            @Value("${otp.hmac-secret}") String secret) {

        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException(
                    "OTP HMAC secret must be at least 32 characters");
        }

        this.hmacSecret =
                secret.getBytes(StandardCharsets.UTF_8);
    }

    public String generateOtp() {
        int number = secureRandom.nextInt(1_000_000);
        return String.format(Locale.ROOT, "%06d", number);
    }

    public String createVerifier(String otp) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");

            SecretKeySpec key = new SecretKeySpec(
                    hmacSecret, "HmacSHA256");

            mac.init(key);

            byte[] hash = mac.doFinal(
                    otp.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);

        } catch (java.security.GeneralSecurityException ex) {
            throw new IllegalStateException(
                    "Unable to create OTP verifier", ex);
        }
    }





// Add this method inside OtpGenerator

    public boolean verifyOtp(String submittedOtp, String storedVerifier) {
        String submittedVerifier = createVerifier(submittedOtp);

        return MessageDigest.isEqual(
                submittedVerifier.getBytes(StandardCharsets.UTF_8),
                storedVerifier.getBytes(StandardCharsets.UTF_8)
        );
    }

}

