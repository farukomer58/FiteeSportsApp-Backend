package com.fitee.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Freelancer extends User {

    private String lessonType;

    @OneToMany(mappedBy = "owner")
    private List<Activity> ownedActivities = new ArrayList<Activity>();

    public Freelancer(String lessonType) {
        this.lessonType = lessonType;
    }

    public Freelancer() {

    }
}
