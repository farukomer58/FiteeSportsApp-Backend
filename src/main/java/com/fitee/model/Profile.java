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

    @Column(name = "PROFILE_IMAGE")
    private String profileImage;

    @Column(name = "DESCRIPTION")
    private String note;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
