package com.fitee.fiteeapp.repository;

import com.fitee.fiteeapp.model.Activity;
import com.fitee.fiteeapp.model.Booking;
import com.fitee.fiteeapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository  extends JpaRepository<Booking, Long> {

    Optional<Booking> findBookingByBookedByAndBookedActivity(User bookedBy, Activity bookedActivity);
}
