package com.pocbuildings.otp_service.repository;

import com.pocbuildings.otp_service.entity.OtpRecord;
import com.pocbuildings.otp_service.enums.OtpPurpose;

import java.util.Optional;
import java.util.UUID;


import com.pocbuildings.otp_service.enums.OtpStatus;
import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OtpRepository
        extends JpaRepository<OtpRecord, UUID> {




    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM OtpRecord o WHERE o.otpId = :otpId")
    Optional<OtpRecord> findByIdForUpdate(
            @Param("otpId") UUID otpId);

    Optional<OtpRecord>
    findByCustomerIdAndPurposeAndReferenceIdAndStatus(
            String customerId,
            OtpPurpose purpose,
            String referenceId,
            OtpStatus status
    );

    @Modifying(flushAutomatically = true)
    @Query("""
        UPDATE OtpRecord o
        SET o.status = :revokedStatus
        WHERE o.customerId = :customerId
          AND o.purpose = :purpose
          AND o.referenceId = :referenceId
          AND o.status = :activeStatus
        """)
    int revokeActiveOtps(
            @Param("customerId") String customerId,
            @Param("purpose") OtpPurpose purpose,
            @Param("referenceId") String referenceId,
            @Param("activeStatus") OtpStatus activeStatus,
            @Param("revokedStatus") OtpStatus revokedStatus
    );
}
