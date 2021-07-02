package com.lezurex.soundtok.server.rest;

import com.lezurex.soundtok.server.database.entities.User;
import com.lezurex.soundtok.server.database.repositories.ClipRepo;
import com.lezurex.soundtok.server.database.repositories.UserRepo;
import com.lezurex.soundtok.server.exceptions.NotFoundException;
import com.lezurex.soundtok.server.rest.output.SimpleClip;
import com.lezurex.soundtok.server.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ClipRepo clipRepo;

    @GetMapping(value = "/api/user/{username}")
    public User getUser(Authentication authentication, @PathVariable String username) throws NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userRepo.findByUsername(username).orElseThrow(NotFoundException::new);
        if (user == null) return null;
        if (!userDetails.getUsername().equals(user.getUsername()))
            user.setEmail("PRIVATE");
        return user;
    }

    @GetMapping(value = "/api/user/{username}/clips")
    public Collection<SimpleClip> getUserClips(@PathVariable String username, Authentication authentication) throws NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userRepo.findByUsername(username).orElseThrow(NotFoundException::new);
        List<SimpleClip> clips = new ArrayList<>();
        clipRepo.findByUser(user).forEach(clip -> clips.add(new SimpleClip(clip, userDetails.getUser())));
        return clips;
    }

}
