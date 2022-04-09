package com.fitee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitee.dto.Address;
import com.fitee.dto.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "UserType")
@Entity()
@Data
@NoArgsConstructor
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

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "PASSWORD")
    private String password;

//    @Embedded //It doesnt create new table for Address
//    private Address address;

    @Column(name = "LOCKED") // Account Status
    private boolean locked;

    @Column(name = "BIRTH_DATE")
    @CreationTimestamp                      // LocalDateTime when created
    private LocalDateTime birthDate;

    @Column(name = "CREATED_DATE")
    @CreationTimestamp                      // LocalDateTime when created
    private LocalDateTime createdDate;

    @Column(name = "USER_ROLE", nullable=true)
    @Enumerated(EnumType.STRING) //EnumType.ORDINAL is default like index of the value
    private UserRole userRole;

    @OneToMany(mappedBy = "owner")
    private List<Activity> ownedActivities = new ArrayList<Activity>();

    @ManyToMany(mappedBy = "participant") //, fetch = FetchType.EAGER
    private List<Activity> joinedActivities = new ArrayList<Activity>();

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "CUSTOMER_ID")
//    private Customer customer;
//
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "FREELANCER_ID")
//    private Freelancer freelancer;

//    @JsonIgnore
//    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
//    private Set<ChatEntity> chatMessages = new HashSet<>();

    /**
     * Convenience method to add a single chat-message
     */
//    public void addChatMessage(ChatEntity chatMessage, UserEntity receiver) {
//        this.chatMessages.add(chatMessage);
//        chatMessage.setSender(this);
//        chatMessage.setReceiver(receiver);
//        chatMessage.setConversationId(this.getId(), receiver.getId());
//    }
}
