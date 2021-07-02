package com.lezurex.soundtok.server;

import com.lezurex.soundtok.server.database.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication
public class ServerApplication {

    @Autowired
    private UserRepo userRepo;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
       log.info("Application started!");
    }

}
