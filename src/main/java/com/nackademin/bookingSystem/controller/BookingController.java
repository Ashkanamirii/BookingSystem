package com.nackademin.bookingSystem.controller;

import com.nackademin.bookingSystem.model.Booking;
import com.nackademin.bookingSystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Ashkan Amiri
 * Date:  2021-06-07
 * Time:  12:24
 * Project: BookingSystem
 * Copyright: MIT
 */
@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("get")
    public List<Booking> getAllBookings(){
        return bookingService.getAll();
    }
    @PostMapping("add")
    public ResponseEntity<Booking> addBooking(@RequestBody Booking booking){
        return ResponseEntity.ok(bookingService.addBooking(booking));
    }
}
