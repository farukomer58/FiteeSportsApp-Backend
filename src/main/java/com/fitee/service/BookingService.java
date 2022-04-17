package com.fitee.service;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fitee.model.Activity;
import com.fitee.model.Booking;
import com.fitee.model.User;
import com.fitee.repository.ActivityRepository;
import com.fitee.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;

    /**
     * Create and Save the product to the database, we also check for a Product Image if provided
     * And also Product Discounnt if provided
     *
     * @param queryMap The JSON productData received From the Frontend / User
     */
    public Booking createBooking(ObjectNode queryMap) {

        Booking booking = new Booking();

        return bookingRepository.save(booking);
    }

    /**
     * Get All bookings for the signed in User
     * */
    public List<Booking> getAllUserBookings() {
        final User currentUser = userService.getCurrentUser();
        return currentUser.getBookings();
    }

    public boolean makePayment() {
        return false;
    }

    public boolean sendConfirmation(long userId) {
        return false;
    }

}
