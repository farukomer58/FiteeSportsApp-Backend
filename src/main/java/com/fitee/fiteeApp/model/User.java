package com.fitee.fiteeApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    //@Column(name = "LOCKED") // Account Status 0=unlocked, 1=locked
    //private Boolean locked = false;

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
    @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns =
    @JoinColumn(name = "ROLE_ID"))
    private List<RoleEntity> roles = new ArrayList<>();
    //private RoleEntity role;

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

    @OneToMany(mappedBy = "bookedBy")                       // One User Has / Can have Many Bookings
    private List<Booking> bookings = new ArrayList<Booking>();


    public User(String firstName, String lastName, String email, String password, String userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    /** ----------------------------------------------------------------------------------------------- */

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

    /** ----------------------------------------------------------------------------------------------- */


    /** Security User Config */
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        SimpleGrantedAuthority authority =
//                new SimpleGrantedAuthority(this.getRole().getName().name());
//        return Collections.singletonList(authority);
//    }
//    @Override
//    public String getUsername() {
//        return getEmail();
//    }
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
