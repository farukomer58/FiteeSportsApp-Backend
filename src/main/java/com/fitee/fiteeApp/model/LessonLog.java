package com.fitee.fiteeApp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity()
@Data
@NoArgsConstructor
@ToString
@Table(name = "Fitee_LessonLog")
public class LessonLog {

    @Id
    @Column(name = "LOG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DESCRIPTION")
    private String note;

    @ManyToOne
    @JoinColumn(name = "ACTIVITY_ID")
    private Activity activity;

}
