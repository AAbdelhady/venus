package com.venus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venus.domain.entities.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByLoginId(String loginId);
}
