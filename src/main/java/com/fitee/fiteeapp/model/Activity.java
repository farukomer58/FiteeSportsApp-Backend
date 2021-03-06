package com.fitee.fiteeapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Fitee_Activity")
public class Activity {

    @Id
    @Column(name = "ACTIVITY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATED_DATE")
    @CreationTimestamp                                      // LocalDateTime when created
    private LocalDateTime createdDate;

    @Column(name = "COVER_IMAGE")
    private String coverImage;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "activity")
    // Add variable name of ChatGroup, ChatGroup is the owning side of relationship
    private ChatGroup chatGroup;

    @Column(name = "OWNER_NAME")
    private String ownerName;

    @Column(name = "ACTIVITY_ADDRESS")
    private String activityAddress;

    @Column(name = "CITY")
    private String city;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    @JsonIgnore
    private User owner;

    @OneToMany(mappedBy = "activity")
    private List<ActivityPrice> activityPrices = new ArrayList<>();

    @OneToMany(mappedBy = "activity")
    private List<ActivityDate> activityDates = new ArrayList<>();

    @ManyToMany()
    @JoinTable(name = "FITEE_ACTIVITY_CATEGORY", joinColumns = @JoinColumn(name = "ACTIVITY_ID"), inverseJoinColumns =
    @JoinColumn(name = "CATEGORY_ID"))
//    @JsonIgnore
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "bookedActivity")                       // One Activity Has / Can have Many Bookings
    @JsonIgnore
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "activity")                       // One Activity Has / Can have Many Reviews
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "activity")                       // One Activity Has / Can have Many lOGS
    @JsonIgnore
    private List<LessonLog> logs = new ArrayList<>();

    private String categoryString;

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

}
