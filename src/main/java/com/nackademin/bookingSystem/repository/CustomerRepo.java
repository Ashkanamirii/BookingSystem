package com.nackademin.bookingSystem.repository;

import com.nackademin.bookingSystem.model.Customer;
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
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    Customer findByEmail(String email);
}
