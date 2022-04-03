package com.fitee.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity()
@Data
@NoArgsConstructor
public class ActivityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTIVITY_TYPE_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activityType")
    private Set<Activity> products = new HashSet<>();


}
