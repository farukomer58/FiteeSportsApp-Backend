package com.fitee.fiteeApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitee.fiteeApp.model.enums.UserRole;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity()
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Fitee_RoleEntity")
public class RoleEntity {

    @Id
    @Column(name = "ROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "NAME")
//    @Enumerated(EnumType.STRING)
//    private UserRole name;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public RoleEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}


