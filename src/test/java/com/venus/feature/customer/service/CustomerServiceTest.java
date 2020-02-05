package com.venus.feature.customer.service;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import com.venus.exceptions.NotFoundException;
import com.venus.feature.common.enums.Role;
import com.venus.feature.customer.dto.CustomerResponse;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.customer.repository.CustomerRepository;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.service.UserService;

import static com.venus.testutils.AssertionUtils.assertCustomerEqualsResponse;
import static com.venus.testutils.AssertionUtils.assertUserEqualsResponse;
import static com.venus.testutils.MapperTestUtils.customerMapper;
import static com.venus.testutils.UnitTestUtils.createDummyCustomer;
import static com.venus.testutils.UnitTestUtils.createDummyUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    private CustomerService service;

    @Mock
    private UserService userService;

    @Mock
    private CustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new CustomerService(userService, customerRepository, customerMapper);
    }

    @Test
    public void createCustomer_shouldCreateCustomerForUser() {
        // given
        User user = createDummyUser();
        when(userService.updateAuthorizedUserRole(Role.CUSTOMER)).thenReturn(user);
        when(customerRepository.save(any(Customer.class))).then(customerSaveAnswer());

        // when
        CustomerResponse response = service.createCustomer();

        // then
        assertUserEqualsResponse(user, response.getUser());
    }


    @Test
    public void findCustomerById_shouldReturnCustomer() {
        // given
        Customer customer = createDummyCustomer();
        when(customerRepository.findByUserId(customer.getId())).thenReturn(Optional.of(customer));

        // when
        CustomerResponse response = service.findCustomerById(customer.getId());

        // then
        assertCustomerEqualsResponse(customer, response);
    }

    @Test(expected = NotFoundException.class)
    public void findCustomerById_shouldThrowNotFoundException_whenUserWithIdNotFound() {
        // given
        final long id = -1;
        when(customerRepository.findByUserId(id)).thenReturn(Optional.empty());

        // when
        service.findCustomerById(id);
    }

    private Answer<Customer> customerSaveAnswer() {
        return invocation -> invocation.getArgument(0);
    }
}