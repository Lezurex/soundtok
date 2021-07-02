package com.lezurex.soundtok.server.database.entities;

import com.fasterxml.jackson.databind.JsonSerializable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment implements Serializable {

    public Comment(String content, Date date, User user, List<Comment> answers) {
        this.content = content;
        this.date = date;
        this.user = user;
        this.answers = answers;
    }

    public Comment(String content, Date date, User user) {
        this.content = content;
        this.date = date;
        this.user = user;
        this.answers = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String content;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToMany
    private List<Comment> answers;

    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", user=" + user +
                ", answers=" + answers +
                '}';
    }
}
