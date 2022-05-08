package com.fitee.fiteeApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Getter
@Setter
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
    @JsonIgnore
    private User owner;

//    @ManyToOne
//    @JoinColumn(name = "ACTIVITY_TYPE_ID")
//    private ActivityType activityType;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "activity")
    // Add variable name of ChatGroup, ChatGroup is the owning side of relationship
    private ChatGroup chatGroup;

    @OneToMany(mappedBy = "bookedActivity")                       // One Activity Has / Can have Many Bookings
    @JsonIgnore
    private List<Booking> bookings = new ArrayList<Booking>();

    @OneToMany(mappedBy = "activity")                       // One Activity Has / Can have Many Reviews
    @JsonIgnore
    private List<Review> reviews = new ArrayList<Review>();

    @OneToMany(mappedBy = "activity")                       // One Activity Has / Can have Many lOGS
    @JsonIgnore
    private List<LessonLog> logs = new ArrayList<LessonLog>();

    @ManyToMany()
    @JoinTable(name = "ACTIVITY_PARTICIPANT", joinColumns = @JoinColumn(name = "ACTIVITY_ID"), inverseJoinColumns =
    @JoinColumn(name = "PARTICIPANT_ID"))
    @JsonIgnore
    private List<User> participants = new ArrayList<User>();

    @ManyToMany()
    @JoinTable(name = "ACTIVITY_CATEGORY", joinColumns = @JoinColumn(name = "ACTIVITY_ID"), inverseJoinColumns =
    @JoinColumn(name = "CATEGORY_ID"))
    @JsonIgnore
    private List<Category> categories = new ArrayList<Category>();


    public void addBooking(Booking booking) {
        this.bookings.add(booking);
        booking.setBookedActivity(this);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setActivity(this);
    }

    public void addLog(LessonLog log) {
        this.logs.add(log);
        log.setActivity(this);
    }

    public void addParticipant(User participant) {
        this.participants.add(participant);
    }
}