package com.venus.feature.customer.repository;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.venus.IntegrationTest;
import com.venus.feature.common.enums.Role;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.user.entity.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CustomerRepositoryIntegrationTest extends IntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findByUserId_shouldReturnCorrectCustomer() {
        // given
        User user = createUser(Role.CUSTOMER);
        Customer expected = createCustomer(user);

        // when
        Optional<Customer> actual = customerRepository.findByUserId(user.getId());

        // then
        assertTrue(actual.isPresent());
        assertEquals(expected.getId(), actual.get().getId());
        assertEquals(expected.getUser().getLoginId(), actual.get().getUser().getLoginId());
    }
}