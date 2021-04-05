package org.fasttrackit.online_shop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.online_shop.domain.Customer;
import org.fasttrackit.online_shop.exception.ResourcesNotFoundException;
import org.fasttrackit.online_shop.persistance.CustomerRepository;
import org.fasttrackit.online_shop.transfer.customer.CreateCustomerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.objectMapper = objectMapper;
    }


    public Customer createCustomer(CreateCustomerRequest request) {
        LOGGER.info("Creating customer {}", request);
        Customer customer = objectMapper.convertValue(request, Customer.class);

        return customerRepository.save(customer);
    }

    public Customer getCustomer(long id) {
        LOGGER.info("Get customer {}", id);

        return customerRepository.findById(id).
                orElseThrow(() -> new ResourcesNotFoundException("Customer id" + id + " not found."));
    }
}