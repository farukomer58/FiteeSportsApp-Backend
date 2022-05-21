package com.fitee.fiteeApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Fitee_ActivityDate")
public class ActivityDate {

    @Id
    @Column(name = "ACTIVITY_DATE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ACTIVITY_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime date;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ACTIVITY_ID")
    private Activity activity;

    @JoinColumn(name = "MAX_PARTICIPANTS")
    private Integer maxParticipants;

    @ManyToMany()
    @JoinTable(name = "FITEE_ACTIVITY_PARTICIPANT", joinColumns = @JoinColumn(name = "ACTIVITY_DATE_ID"), inverseJoinColumns =
    @JoinColumn(name = "PARTICIPANT_ID"))
    @JsonIgnore
    private List<User> participants = new ArrayList<User>();

    public void addParticipant(User participant) {
        this.participants.add(participant);
    }

}
