package com.nackademin.bookingSystem.repository;

import com.nackademin.bookingSystem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Ashkan Amiri
 * Date:  2021-06-07
 * Time:  12:18
 * Project: BookingSystem
 * Copyright: MIT
 */
@Repository
public interface BookingRepo extends JpaRepository<Booking,Long>{
}
