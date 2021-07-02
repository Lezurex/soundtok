package com.lezurex.soundtok.server.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements Serializable {

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true, nullable = false)
    @JsonIgnore
    private String email;
    @Column(unique = true, nullable = false)
    private String username;
    @JsonIgnore
    private String password;

    @OneToMany
    @JsonIgnore
    private List<Clip> clips;

    public User() {

    }

}
