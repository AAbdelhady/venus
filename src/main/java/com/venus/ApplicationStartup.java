package com.venus;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.venus.domain.entities.user.Artist;
import com.venus.domain.entities.user.Customer;
import com.venus.domain.entities.user.User;
import com.venus.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        List<User> users = Arrays.asList(new Artist(), new Customer());
        users = userRepository.saveAll(users);
        log.info("Saved User", users);
    }
}
