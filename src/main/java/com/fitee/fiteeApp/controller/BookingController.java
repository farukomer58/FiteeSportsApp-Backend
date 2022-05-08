package com.fitee.fiteeApp.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fitee.fiteeApp.model.Booking;
import com.fitee.fiteeApp.service.BookingService;
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
     *
     * @return
     */
    @GetMapping("")
    public List<Booking> getAllBookings() {
        return bookingService.getAllUserBookings();
    }

    /**
     * POST: Save / Create booking to database and sends a verification email.
     *
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<Object> createBooking(@RequestBody ObjectNode queryMap) throws MessagingException {
        final Booking booking = bookingService.createBooking(queryMap);

        // String jwtToken = jwtProvider.createVerifyingToken(newUser.getUsername());
        // userService.sendVerifyingEmail(user, jwtToken);

        // Current Request URI creation
        URI location =
                ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/users/{id}").buildAndExpand(booking.getId()).toUri();
        // Custom header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", location.toString());
        // Response code CREATED with Custom body
        return new ResponseEntity<>(booking, headers, HttpStatus.CREATED);
    }



}
