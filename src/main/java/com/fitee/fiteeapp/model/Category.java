package com.fitee.fiteeapp.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Fitee_Category")
public class Category {

    @Id
    @Column(name = "CATEGORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CATEGORY")
    private String category;

    @ManyToMany(mappedBy = "categories") //, fetch = FetchType.EAGER
    @JsonIgnore
    private List<Activity> activities = new ArrayList<Activity>();

}
