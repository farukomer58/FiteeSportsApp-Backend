package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;

@Getter
@Setter
@Entity(name = "TBL_CUSTOMER")
public class User {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

    @JsonIgnore
    @OneToOne(mappedBy = "customer")
    private UserEntity user;
}
