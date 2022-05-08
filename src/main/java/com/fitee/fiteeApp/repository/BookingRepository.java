package com.fitee.fiteeApp.repository;

import com.fitee.fiteeApp.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository  extends JpaRepository<Booking, Long> {
}
