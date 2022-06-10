package com.fitee.fiteeapp.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fitee.fiteeapp.model.Booking;
import com.fitee.fiteeapp.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private final BookingService bookingService;

    /**
     * GET: Get all Bookings for user
     * @return All Bookings for logged in user
     */
    @GetMapping("")
    public List<Booking> getAllBookings() {
        return bookingService.getAllUserBookings();
    }

    /**
     * GET: Get Booking By Id
     * @return Found booking
     */
    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable long id) {
        return bookingService.getBookingById(id);
    }

    /**
     * POST: Save / Create booking to database and sends a verification email.
     * @return Responseentity with created http status and URI to created booking
     */
    @PostMapping("")
    public ResponseEntity<Object> createBooking(@RequestBody ObjectNode queryMap) throws MessagingException {
        final Booking booking = bookingService.createBooking(queryMap);

        // Current Request URI creation
        URI location =
                ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/bookings/{id}").buildAndExpand(booking.getId()).toUri();
        // Custom header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", location.toString());
        // Response code CREATED with Custom body
        return new ResponseEntity<>(booking, headers, HttpStatus.CREATED);
    }


}
