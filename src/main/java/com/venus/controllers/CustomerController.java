package com.venus.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.venus.feature.customer.dto.CustomerResponse;
import com.venus.feature.customer.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer() {
        return customerService.createCustomer();
    }

    @GetMapping("{id}")
    public CustomerResponse findCustomerById(@PathVariable Long id) {
        return customerService.findCustomerById(id);
    }
}
