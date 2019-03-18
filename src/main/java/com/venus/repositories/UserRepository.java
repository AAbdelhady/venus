package com.venus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venus.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
