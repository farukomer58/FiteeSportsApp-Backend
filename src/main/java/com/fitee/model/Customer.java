package com.fitee.model;

import com.fitee.dto.Address;
import com.fitee.dto.BankAccount;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends User {

//    @Embedded //It doesnt create new table for Address
//    private Address address;

    private String address;

    @Embedded
    private BankAccount bankDetails;

    @ManyToMany(mappedBy = "participant") //, fetch = FetchType.EAGER
    private List<Activity> joinedActivities = new ArrayList<Activity>();

    public Customer() {

    }
}
