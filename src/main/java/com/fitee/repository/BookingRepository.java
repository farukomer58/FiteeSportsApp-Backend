package com.fitee.repository;

import com.fitee.model.Activity;
import com.fitee.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository  extends JpaRepository<Booking, Long> {
}
