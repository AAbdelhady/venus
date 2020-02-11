package com.venus;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.venus.test.service.DummyArtistService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final DummyArtistService dummyArtistService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        // insertRandomArtists();
    }

    private void insertRandomArtists() {
        log.info("STARTING INSERTING 100 ARTISTS");
        long startTime = System.currentTimeMillis();
        dummyArtistService.insertDummyArtists(100);
        long diff = System.currentTimeMillis() - startTime;
        log.info("END INSERTING 100 ARTISTS, TIME TAKEN: {} sec", diff / 1000);
    }
}
