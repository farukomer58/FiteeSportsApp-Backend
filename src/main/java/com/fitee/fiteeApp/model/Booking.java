package com.fitee.fiteeApp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity()
@Data
@NoArgsConstructor
public class Booking {

    @Id
    @Column(name = "BOOKING_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BOOKING_DATE")
    private Date createdDate = new Date(System.currentTimeMillis());

    @Column(name = "PAYMENT_STATUS") // Account Status
    private String paymentStatus;

    @Column(name="TOTAL_AMOUNT")
    private float totalAmount;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User bookedBy;

    @ManyToOne
    @JoinColumn(name = "ACTIVITY_ID")
    private Activity bookedActivity;
}
