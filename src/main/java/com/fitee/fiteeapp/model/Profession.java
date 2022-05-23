package com.fitee.fiteeapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity()
@Data
@NoArgsConstructor
@ToString
@Table(name = "Fitee_Profession")
public class Profession {
    @Id
    @Column(name = "PROFESSION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PROFESSION")
    private String profession;

    @ManyToMany(mappedBy = "professions")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

}
