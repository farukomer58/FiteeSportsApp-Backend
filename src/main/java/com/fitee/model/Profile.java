package com.fitee.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity()
@Data
@NoArgsConstructor
public class Profile {

    @Id
    @Column(name = "PROFILE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String profileImage;

    @Column(name="BIO")
    private String bio;

    @OneToOne(fetch = FetchType.LAZY, mappedBy="profile") // Add variable name of User, User is the owning side of relationship
    private User user;
}
