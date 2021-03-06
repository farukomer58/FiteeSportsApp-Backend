package com.fitee.fiteeapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity()
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Fitee_ActivityPrice")
public class ActivityPrice {

    @Id
    @Column(name = "ACTIVITY_PRICE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "LESSONS")
    private Integer lessons;

    @JoinColumn(name = "PRICE")
    private BigDecimal price;

    @JoinColumn(name = "DISCOUNT")
    private BigDecimal discount;


    public ActivityPrice(Integer lessons, BigDecimal price, BigDecimal discount) {
        this.lessons = lessons;
        this.price = price;
        this.discount = discount;
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ACTIVITY_ID")
    private Activity activity;

}
