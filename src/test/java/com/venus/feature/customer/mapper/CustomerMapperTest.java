package com.venus.feature.customer.mapper;

import org.junit.Test;

import com.venus.feature.customer.dto.CustomerResponse;
import com.venus.feature.customer.entity.Customer;

import static com.venus.testutils.AssertionUtils.assertCustomerEqualsResponse;
import static com.venus.testutils.MapperTestUtils.customerMapper;
import static com.venus.testutils.UnitTestUtils.createDummyCustomer;

public class CustomerMapperTest {

    @Test
    public void mapOne_shouldMapCustomerToResponse() {
        // given
        Customer customer = createDummyCustomer();

        // when
        CustomerResponse response = customerMapper.mapOne(customer);

        // then
        assertCustomerEqualsResponse(customer, response);
    }
}