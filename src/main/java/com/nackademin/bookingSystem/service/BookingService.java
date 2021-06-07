package com.nackademin.bookingSystem.service;

import com.nackademin.bookingSystem.model.Booking;
import com.nackademin.bookingSystem.repository.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ashkan Amiri
 * Date:  2021-06-07
 * Time:  12:23
 * Project: BookingSystem
 * Copyright: MIT
 */
@Service
public class BookingService {
    @Autowired
    private BookingRepo bookingRepo;


    public Booking addBooking(Booking booking){
       return bookingRepo.save(booking);
    }

    public List<Booking> getAll() {
        return bookingRepo.findAll();
    }
}
