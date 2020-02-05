package com.venus.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import com.venus.feature.customer.dto.CustomerResponse;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.customer.service.CustomerService;

import static com.venus.testutils.MapperTestUtils.customerMapper;
import static com.venus.testutils.UnitTestUtils.createDummyCustomer;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends MvcTest {

    @InjectMocks
    CustomerController controller;

    @Mock
    CustomerService customerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        init(controller);
    }

    @Test
    public void createCustomer() throws Exception {
        // given
        Customer customer = createDummyCustomer();
        CustomerResponse response = customerMapper.mapOne(customer);

        given(customerService.createCustomer()).willReturn(response);

        // when
        mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(customer.getId()))
                .andExpect(jsonPath("$.firstName").value(customer.getUser().getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getUser().getLastName()))
                .andExpect(jsonPath("$.role").value(customer.getUser().getRole().name()));
    }

    @Test
    public void findCustomerById() throws Exception {
        // given
        Customer customer = createDummyCustomer();
        CustomerResponse response = customerMapper.mapOne(customer);

        given(customerService.findCustomerById(1L)).willReturn(response);

        // when
        mockMvc.perform(get("/customer/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customer.getId()))
                .andExpect(jsonPath("$.firstName").value(customer.getUser().getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getUser().getLastName()))
                .andExpect(jsonPath("$.role").value(customer.getUser().getRole().name()));
    }
}