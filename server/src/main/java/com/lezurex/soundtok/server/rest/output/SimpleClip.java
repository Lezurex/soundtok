package com.lezurex.soundtok.server.rest.output;

import com.lezurex.soundtok.server.database.entities.Clip;
import com.lezurex.soundtok.server.database.entities.User;

import java.io.Serializable;
import java.util.Date;

public class SimpleClip implements Serializable {

    public SimpleClip(Clip clip, User user) {
        this.id = clip.getId();
        this.caption = clip.getCaption();
        this.user = clip.getUser();
        this.date = clip.getDate();
        this.views = clip.getViews().size();
        this.likes = clip.getLikes().size();
        this.comments = clip.getComments().size();
        this.viewed = clip.getViews().stream().anyMatch(viewer -> viewer.getId() == user.getId());
        this.liked = clip.getLikes().stream().anyMatch(liker -> liker.getId() == user.getId());
    }

    public final int id;
    public final String caption;
    public final User user;
    public final Date date;
    public final int views;
    public final int likes;
    public final int comments;
    public final boolean viewed;
    public final boolean liked;
}
