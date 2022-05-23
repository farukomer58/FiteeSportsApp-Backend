package com.fitee.fiteeapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity()
@Data
@NoArgsConstructor
@ToString
@Table(name = "Fitee_Profile")
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
