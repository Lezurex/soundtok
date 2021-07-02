package com.lezurex.soundtok.server.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clips")
@Getter
@Setter
public class Clip implements Serializable {

    public Clip(String caption, Date date, User user, byte[] audio, List<User> views, List<User> likes, List<Comment> comments) {
        this.caption = caption;
        this.date = date;
        this.user = user;
        this.audio = audio;
        this.views = views;
        this.likes = likes;
        this.comments = comments;
    }

    public Clip(String caption, Date date, User user, byte[] audio) {
        this.caption = caption;
        this.date = date;
        this.user = user;
        this.audio = audio;
        this.views = new ArrayList<>();
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String caption;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Lob
    @JsonIgnore
    private byte[] audio;

    @ManyToMany
    @JsonIgnore
    private List<User> views;

    @ManyToMany
    private List<User> likes;

    @ManyToMany
    private List<Comment> comments;

    public Clip() {}

    @Override
    public String toString() {
        return "Clip{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", date=" + date +
                ", user=" + user +
                ", views=" + views +
                ", likes=" + likes +
                ", comments=" + comments +
                '}';
    }
}
