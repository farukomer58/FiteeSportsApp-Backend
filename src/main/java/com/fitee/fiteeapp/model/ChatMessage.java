package com.fitee.fiteeapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity()
@Data
@NoArgsConstructor
@ToString
@Table(name = "Fitee_ChatMessage")
public class ChatMessage {

    @Id
    @Column(name = "MESSAGE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "DATE_CREATED")
    private Date createdDate = new Date(System.currentTimeMillis());

    @ManyToOne
    @JoinColumn(name = "MESSAGE_OWNER")
    private User messageOwner;

    @ManyToOne
    @JoinColumn(name = "CHAT_GROUP")
    private ChatGroup chatGroup;

}
