package com.lezurex.soundtok.server.database.repositories;

import com.lezurex.soundtok.server.database.entities.Clip;
import com.lezurex.soundtok.server.database.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClipRepo extends CrudRepository<Clip, Integer> {

    List<Clip> findByUser(User user);

}
