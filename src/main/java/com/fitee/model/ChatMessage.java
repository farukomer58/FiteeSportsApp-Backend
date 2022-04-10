package com.fitee.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity()
@Data
@NoArgsConstructor
public class ChatMessage {

    @Id
    @Column(name = "MESSAGE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MESSAGE")
    private String message;

    @ManyToOne
    @JoinColumn(name = "MESSAGE_OWNER")
    private User messageOwner;

    @ManyToOne
    @JoinColumn(name = "CHAT_GROUP")
    private ChatGroup chatGroup;

}
