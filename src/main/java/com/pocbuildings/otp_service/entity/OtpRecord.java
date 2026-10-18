package com.pocbuildings.otp_service.entity;

import com.pocbuildings.otp_service.enums.NotificationChannel;
import com.pocbuildings.otp_service.enums.OtpPurpose;
import com.pocbuildings.otp_service.enums.OtpStatus;

import java.time.Instant;
import java.util.UUID;




import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Entity
@Table(name = "OTP_RECORD")
public class OtpRecord {

    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "OTP_ID", nullable = false, length = 36)
    private UUID otpId;

    @Column(name = "CUSTOMER_ID", nullable = false, length = 64)
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "PURPOSE", nullable = false, length = 40)
    private OtpPurpose purpose;

    @Column(name = "REFERENCE_ID", nullable = false, length = 100)
    private String referenceId;

    @Column(name = "RECIPIENT_REFERENCE",
            nullable = false, length = 150)
    private String recipientReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "CHANNEL", nullable = false, length = 10)
    private NotificationChannel channel;

    @Column(name = "OTP_VERIFIER",
            nullable = false, length = 128)
    private String otpVerifier;



    @Column(name = "ATTEMPT_COUNT", nullable = false)
    private int attemptCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 12)
    private OtpStatus status;

    @Column(name = "CREATED_ON", nullable = false)
    private Instant createdOn;

    @Column(name = "EXPIRES_AT", nullable = false)
    private Instant expiresAt;

    public OtpRecord() {
        // Required by JPA
    }

    public UUID getOtpId() {
        return otpId;
    }

    public void setOtpId(UUID otpId) {
        this.otpId = otpId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public OtpPurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(OtpPurpose purpose) {
        this.purpose = purpose;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getRecipientReference() {
        return recipientReference;
    }

    public void setRecipientReference(String recipientReference) {
        this.recipientReference = recipientReference;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public String getOtpVerifier() {
        return otpVerifier;
    }

    public void setOtpVerifier(String otpVerifier) {
        this.otpVerifier = otpVerifier;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }

    public OtpStatus getStatus() {
        return status;
    }

    public void setStatus(OtpStatus status) {
        this.status = status;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }
}
