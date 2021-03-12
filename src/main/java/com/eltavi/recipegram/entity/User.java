package com.eltavi.recipegram.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String imageProfile;

    private String nickname;

    private String descriptionProfile;

    @ManyToMany
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "id_follower"),
            inverseJoinColumns = @JoinColumn(name = "id_following")
    )
    private Set<User> subscriptions = new HashSet<>();

}
