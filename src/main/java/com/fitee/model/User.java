package com.fitee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
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

    @Column(name = "LOCKED") // Account Status 0=unlocked, 1=locked
    private Integer locked = 1;

    @Column(name = "BIRTH_DATE")
    @CreationTimestamp                      // LocalDateTime when created
    private LocalDateTime birthDate;

    @Column(name = "CREATED_DATE")
    @CreationTimestamp                      // LocalDateTime when created
    private LocalDateTime createdDate;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Profile profile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID")
    @JsonIgnore
    private RoleEntity role;

    @OneToMany(mappedBy = "messageOwner")
    @JsonIgnore
    private List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();

    @ManyToMany(mappedBy = "groupMembers") //, fetch = FetchType.EAGER
    @JsonIgnore
    private List<ChatGroup> joinedChatGroups = new ArrayList<ChatGroup>();

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Activity> ownedActivities = new ArrayList<Activity>();

    @ManyToMany(mappedBy = "participants") //, fetch = FetchType.EAGER
    @JsonIgnore
    private List<Activity> joinedActivities = new ArrayList<Activity>();

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
//        chatMessage.setConversationId(this.getId(), receiver.getId());
    }
}
