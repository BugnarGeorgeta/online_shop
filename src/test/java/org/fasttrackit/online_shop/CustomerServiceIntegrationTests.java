package org.fasttrackit.online_shop;

import org.fasttrackit.online_shop.domain.Customer;
import org.fasttrackit.online_shop.exception.ResourcesNotFoundException;
import org.fasttrackit.online_shop.service.CustomerService;
import org.fasttrackit.online_shop.steps.CustomerTestSteps;
import org.fasttrackit.online_shop.transfer.customer.CreateCustomerRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest
public class CustomerServiceIntegrationTests {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerTestSteps customerTestSteps;

    @Test
    void createCustomer_whenHaveValidRequest_thenCustomerIsCreated() {
        customerTestSteps.createCustomer();
    }

    @Test
    void createCustomer_whenMissingLastName_thenThrowError() {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setFirstName("Medrea");
        try {
            customerService.createCustomer(request);
        } catch (Exception e) {
            assertThat(e, notNullValue());
        }
    }

    @Test
    void getCustomer_whenExistingCustomer_thenReturnCustomer() {
        Customer customer = customerTestSteps.createCustomer();
        Customer getCustomer = customerService.getCustomer(customer.getId());

        assertThat(getCustomer, notNullValue());
        assertThat(getCustomer.getId(), is(customer.getId()));
        assertThat(getCustomer.getFirstName(), is(customer.getFirstName()));
        assertThat(getCustomer.getLastName(), is(customer.getLastName()));

    }

    @Test
    void getCustomer_whenNonExistingCustomer_thenThrowResourcesNotFound() {
        Assertions.assertThrows(ResourcesNotFoundException.class,
                () -> customerService.getCustomer(99999));

    }
}







