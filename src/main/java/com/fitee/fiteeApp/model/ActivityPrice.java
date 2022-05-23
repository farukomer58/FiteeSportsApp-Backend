package com.fitee.fiteeApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
