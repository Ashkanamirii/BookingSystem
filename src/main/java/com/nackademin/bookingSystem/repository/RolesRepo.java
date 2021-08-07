package com.nackademin.bookingSystem.repository;

import com.nackademin.bookingSystem.model.Customer;
import com.nackademin.bookingSystem.model.RolesCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Hodei Eceiza
 * Date: 6/15/2021
 * Time: 23:08
 * Project: BookingSystem
 * Copyright: MIT
 */
@Repository
public interface RolesRepo extends JpaRepository<RolesCustomer,Long> {
    RolesCustomer findByRoleType(String role);
}
