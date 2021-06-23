package com.nackademin.bookingSystem.repository;

import com.nackademin.bookingSystem.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Hodei Eceiza
 * Date: 6/23/2021
 * Time: 22:40
 * Project: BookingSystem
 * Copyright: MIT
 */
@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken,Long> {
}
