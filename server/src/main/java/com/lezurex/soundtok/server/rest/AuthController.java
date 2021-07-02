package com.lezurex.soundtok.server.rest;

import com.lezurex.soundtok.server.database.entities.User;
import com.lezurex.soundtok.server.database.repositories.UserRepo;
import com.lezurex.soundtok.server.rest.input.RegisterCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping(value = "/api/auth", produces = "application/json")
    public String getStatus(Authentication authentication) throws JSONException {
        JSONObject response = new JSONObject();
        response.put("authenticated", authentication != null);
        return response.toString();
    }

    @PostMapping(value = "/api/auth/register", produces = "application/json")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String email) throws JSONException {
        JSONObject response = new JSONObject();
        if (username != null) {
            if (!userRepo.findByUsername(username).isPresent()) {
                if (!userRepo.findByEmail(email).isPresent()) {
                    User user = new User(email, username, passwordEncoder.encode(password));
                    userRepo.save(user);
                    response.put("code", 200);
                } else
                    response.put("error", "Email already registered").put("code", 1010);
            } else
                response.put("error", "Username already registered").put("code", 1011);
        } else
            response.put("error", "Bad request").put("code", 404);
        return response.toString();
    }

}
