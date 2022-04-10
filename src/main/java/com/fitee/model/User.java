package com.fitee.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "USER_ROLE", nullable=true)
    @Enumerated(EnumType.STRING) //EnumType.ORDINAL is default like index of the value
    private UserRole userRole;


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
