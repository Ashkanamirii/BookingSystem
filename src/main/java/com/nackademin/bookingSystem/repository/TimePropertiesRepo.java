package com.nackademin.bookingSystem.repository;

import com.nackademin.bookingSystem.model.TimeProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Ashkan Amiri
 * Date:  2021-06-07
 * Time:  12:19
 * Project: BookingSystem
 * Copyright: MIT
 */
@Repository
public interface TimePropertiesRepo extends JpaRepository<TimeProperties,Long> {
}
