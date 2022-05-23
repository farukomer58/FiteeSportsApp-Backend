package com.fitee.fiteeapp.repository;

import com.fitee.fiteeapp.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository  extends JpaRepository<Booking, Long> {
}
