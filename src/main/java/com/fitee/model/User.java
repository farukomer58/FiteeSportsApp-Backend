package com.fitee.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "UserType")
@Entity()
@Data
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "USER_ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "LOCKED") // Account Status
    private boolean locked;

    @Column(name = "BIRTH_DATE")
    @CreationTimestamp                      // LocalDateTime when created
    private LocalDateTime birthDate;

    @Column(name = "CREATED_DATE")
    @CreationTimestamp                      // LocalDateTime when created
    private LocalDateTime createdDate;

    @Column(name = "USER_ROLE", nullable = true)
    @Enumerated(EnumType.ORDINAL) //EnumType.ORDINAL is default like index of the value
    private UserRole userRole;

    @OneToMany(mappedBy = "messageOwner")
    private List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();

    @ManyToMany(mappedBy = "groupMembers") //, fetch = FetchType.EAGER
    private List<ChatGroup> joinedChatGroups = new ArrayList<ChatGroup>();

    public void joinGroup(ChatGroup chatGroup) {
        this.joinedChatGroups.add(chatGroup);
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
