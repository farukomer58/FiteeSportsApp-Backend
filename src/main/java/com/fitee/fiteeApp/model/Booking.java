package com.fitee.fiteeApp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity()
@Data
@NoArgsConstructor
@ToString
@Table(name = "Fitee_Booking")
public class Booking {

    @Id
    @Column(name = "BOOKING_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "QUANTITY")
    private Long quantity; // Number of tickets/lessons purchased

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
