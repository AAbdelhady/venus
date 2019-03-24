package com.venus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venus.domain.entities.user.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
