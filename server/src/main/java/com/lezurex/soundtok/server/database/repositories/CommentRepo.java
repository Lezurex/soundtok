package com.lezurex.soundtok.server.database.repositories;

import com.lezurex.soundtok.server.database.entities.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepo extends CrudRepository<Comment, Integer> {
}
