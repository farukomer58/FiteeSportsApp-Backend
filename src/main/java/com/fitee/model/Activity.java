package com.fitee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity()
@Data
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private Freelancer owner;

//    @ManyToOne
//    @JoinColumn(name = "ACTIVITY_TYPE_ID")
//    private ActivityType activityType;

    @OneToMany(mappedBy = "bookedActivity")                       // One User Has / Can have Many Bookings
    private List<Booking> bookings = new ArrayList<Booking>();

    @OneToMany(mappedBy = "activity")                       // One Activity Has / Can have Many Reviews
    private List<Review> reviews = new ArrayList<Review>();

    @OneToMany(mappedBy = "activity")                       // One Activity Has / Can have Many lOGS
    private List<LessonLog> logs = new ArrayList<LessonLog>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy="activity") // Add variable name of ChatGroup, ChatGroup is the owning side of relationship
    private ChatGroup chatGroup;

    @ManyToMany()
    @JoinTable(name = "ACTIVITY_PARTICIPANT", joinColumns = @JoinColumn(name = "ACTIVITY_ID"), inverseJoinColumns =
    @JoinColumn(name = "PARTICIPANT_ID"))
    @JsonIgnore
    private List<Customer> participant = new ArrayList<Customer>();

}
