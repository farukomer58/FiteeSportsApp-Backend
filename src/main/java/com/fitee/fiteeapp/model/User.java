package com.fitee.fiteeapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Inheritance(strategy = InheritanceType.JOINED)
@Entity()
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Fitee_User")
public class User {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "BIRTH_DATE")
    @CreationTimestamp                      // LocalDateTime when created
    private LocalDateTime birthDate;

    @Column(name = "CREATED_DATE")
    @CreationTimestamp                      // LocalDateTime when created
    private LocalDateTime createdDate;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Profile profile;

    @Column(name = "USER_ROLE")
    private String userRole;

    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "ROLE_ID")
//    @JsonIgnore
    @JoinTable(name = "FITEE_USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns =
    @JoinColumn(name = "ROLE_ID"))
    private List<RoleEntity> roles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "FITEE_USER_PROFESSION", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns =
    @JoinColumn(name = "PROFESSION_ID"))
    private List<Profession> professions = new ArrayList<>();

    @OneToMany(mappedBy = "messageOwner")
    @JsonIgnore
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @ManyToMany(mappedBy = "groupMembers") //, fetch = FetchType.EAGER
    @JsonIgnore
    private List<ChatGroup> joinedChatGroups = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Activity> ownedActivities = new ArrayList<>();

    @ManyToMany(mappedBy = "participants") //, fetch = FetchType.EAGER
    @JsonIgnore
    private List<ActivityDate> joinedActivities = new ArrayList<>();

    @OneToMany(mappedBy = "bookedBy")                       // One User Has / Can have Many Bookings
    private List<Booking> bookings = new ArrayList<>();


    public User(String firstName, String lastName, String email, String password, String userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    /**
     * -----------------------------------------------------------------------------------------------
     */

    public void joinGroup(ChatGroup chatGroup) {
        this.joinedChatGroups.add(chatGroup);
    }

    public void addOwnedActivities(Activity ownedActivity) {
        this.ownedActivities.add(ownedActivity);
    }

    /**
     * Convenience method to add a single chat-message
     */
    public void addChatMessage(ChatMessage chatMessage, ChatGroup receiver) {
        this.chatMessages.add(chatMessage);
        chatMessage.setMessageOwner(this);
        chatMessage.setChatGroup(receiver);
    }

    /** ----------------------------------------------------------------------------------------------- */

}
