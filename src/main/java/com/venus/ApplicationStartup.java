package com.venus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.venus.services.artist.ArtistService;
import com.venus.services.user.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private ArtistService artistService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    }
}
