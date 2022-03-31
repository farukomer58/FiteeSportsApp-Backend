package com.fitee.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Activity {

    @Id
    @Column(name = "ACTIVITY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

//    @Column(name = "UNIT")
//    private String unit;
//
//    @Column(name = "QUANTITY")
//    private Double quantity;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "CREATED_DATE")
    @CreationTimestamp                                      // LocalDateTime when created
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "activity")                       // One Activity Has / Can have Many Reviews
    private List<Review> reviews = new ArrayList<Review>();


}
