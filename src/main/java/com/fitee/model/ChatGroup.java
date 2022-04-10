package com.fitee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Data
@NoArgsConstructor
public class ChatGroup {

    @Id
    @Column(name = "GROUP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ACTIVITY_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Activity activity;

    @OneToMany(mappedBy = "chatGroup")
    private List<ChatMessage> groupMessages = new ArrayList<ChatMessage>();

    @ManyToMany()
    @JoinTable(name = "CHAT_GROUP_MEMBER", joinColumns = @JoinColumn(name = "GROUP_ID"), inverseJoinColumns =
    @JoinColumn(name = "PARTICIPANT_ID"))
    @JsonIgnore
    private List<User> groupMember = new ArrayList<User>();

}
