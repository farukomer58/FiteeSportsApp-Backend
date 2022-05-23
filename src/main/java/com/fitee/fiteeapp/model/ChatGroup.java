package com.fitee.fiteeapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Data
@NoArgsConstructor
@ToString
@Table(name = "Fitee_ChatGroup")
public class ChatGroup {

    @Id
    @Column(name = "GROUP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Column(name = "ACTIVITY_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Activity activity;

    @OneToMany(mappedBy = "chatGroup")
    private List<ChatMessage> groupMessages = new ArrayList<>();

    @ManyToMany()
    @JoinTable(name = "FITEE_CHAT_GROUP_MEMBER", joinColumns = @JoinColumn(name = "GROUP_ID"), inverseJoinColumns =
    @JoinColumn(name = "PARTICIPANT_ID"))
    @JsonIgnore
    private List<User> groupMembers = new ArrayList<>();

    public void addGroupMessage(ChatMessage groupMessage) {
        this.groupMessages.add(groupMessage);
    }

    public void addGroupMember(User newUser) {
        this.groupMembers.add(newUser);
    }

    public boolean removeGroupMember(User groupMember) {
        return this.groupMembers.remove(groupMember);
    }
}
