package com.fitee.fiteeapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fitee.fiteeapp.model.Booking;
import com.fitee.fiteeapp.model.User;
import com.fitee.fiteeapp.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ActivityService activityService;


    /**
     * Get All bookings for the signed-in User
     *
     * @return List with all the bookings made by the User
     */
    public List<Booking> getAllUserBookings() {
        final User currentUser = userService.getCurrentUser();
        return currentUser.getBookings();
    }

    /**
     * Get Booking By Id
     *
     * @param id booking Id
     * @return the found booking with the given id
     */
    public Booking getBookingById(long id) {
        return bookingRepository.findById(id).get();
    }

    /**
     * Create and Save a booking to the database
     *
     * @param queryMap The JSON bookingData received From the Frontend / User with user and activity info
     * @return The created Booking
     */
    public Booking createBooking(ObjectNode queryMap) {

        System.out.println(queryMap);

        final Integer numberOfLessons = queryMap.get("numberOfLessons").asInt();
        final Double totalAmount = queryMap.get("totalAmount").asDouble();
        final Integer activityId = queryMap.get("activityId").asInt();
        final Integer userId = queryMap.get("userId").asInt();

        Booking booking = new Booking();
        booking.setQuantity(numberOfLessons);
        booking.setRemainingAmount(numberOfLessons);
    booking.setTotalAmount(totalAmount);

//        booking.setTotalAmount();
//        booking.setPaymentStatus();

        // Link the tickets to user to the correct activity and User
        booking.setBookedBy(userService.findById(userId));
        booking.setBookedActivity(activityService.findById(activityId));

        // Save Booking

        // TODO: Send verification Mail

        return bookingRepository.save(booking);
    }

    public boolean makePayment() {
        return false;
    }


    public Booking checkIfUserHasTicket(long activityId) {

        final Optional<Booking> bookingByBookedByAndBookedActivity =
                bookingRepository.findBookingByBookedByAndBookedActivity(userService.getCurrentUser(),
                        activityService.findById(activityId));
        System.out.println(bookingByBookedByAndBookedActivity);

        if (bookingByBookedByAndBookedActivity.isEmpty()) {
            return null;
        }
        if (bookingByBookedByAndBookedActivity.get().getRemainingAmount() <= 0) {
            return null;
        }

        return bookingByBookedByAndBookedActivity.get();
    }

    // TODO: Create endpoint to accept activityDate and bookingDetails to store changes

}
