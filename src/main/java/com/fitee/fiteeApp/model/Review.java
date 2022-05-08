package com.fitee.fiteeApp.model;

import com.fitee.fiteeApp.model.enums.ReviewRating;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity()
@Data
@NoArgsConstructor
public class Review {

    @Id
    @Column(name = "REVIEW_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) //EnumType.ORDINAL is default like index of the value
    private ReviewRating rating;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "ACTIVITY_ID")
    private Activity activity;

}
