package com.fitee.fiteeapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fitee.fiteeapp.model.Booking;
import com.fitee.fiteeapp.model.User;
import com.fitee.fiteeapp.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ActivityService activityService;

    /**
     * Create and Save the product to the database, we also check for a Product Image if provided
     * And also Product Discounnt if provided
     *
     * @param queryMap The JSON productData received From the Frontend / User
     */
    public Booking createBooking(ObjectNode queryMap) {

        System.out.println(queryMap);

        final Integer numberOfLessons = queryMap.get("numberOfLessons").asInt();
        final Integer activityId = queryMap.get("activityId").asInt();
        final Integer userId = queryMap.get("userId").asInt();


        // Link the tickets to user to the correct activity

        Booking booking = new Booking();
        booking.setQuantity(numberOfLessons);
        booking.setBookedBy(userService.findById(userId));
        booking.setBookedActivity(activityService.findById(activityId));
//        booking.setTotalAmount();
//        booking.setPaymentStatus();

        return bookingRepository.save(booking);
    }

    /**
     * Get All bookings for the signed in User
     */
    public List<Booking> getAllUserBookings() {
        final User currentUser = userService.getCurrentUser();
        return currentUser.getBookings();
    }

    public boolean makePayment() {
        return false;
    }


}
