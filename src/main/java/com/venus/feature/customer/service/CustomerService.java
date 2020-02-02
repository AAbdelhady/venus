package com.venus.feature.customer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.exceptions.NotFoundException;
import com.venus.feature.common.enums.Role;
import com.venus.feature.customer.dto.CustomerResponse;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.customer.mapper.CustomerMapper;
import com.venus.feature.customer.repository.CustomerRepository;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final UserService userService;

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public CustomerResponse createCustomer() {
        User user = userService.updateAuthorizedUserRole(Role.CUSTOMER);
        Customer customer = new Customer();
        customer.setUser(user);
        customer = customerRepository.save(customer);
        return customerMapper.mapOne(customer);
    }

    public CustomerResponse findCustomerById(Long id) {
        Customer customer = customerRepository.findByUserId(id).orElseThrow(NotFoundException::new);
        return customerMapper.mapOne(customer);
    }
}
