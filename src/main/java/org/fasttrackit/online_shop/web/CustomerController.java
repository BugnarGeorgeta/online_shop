package org.fasttrackit.online_shop.web;

import org.fasttrackit.online_shop.domain.Customer;
import org.fasttrackit.online_shop.service.CustomerService;
import org.fasttrackit.online_shop.transfer.customer.CreateCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody @Validated CreateCustomerRequest request) {
        Customer customer = customerService.createCustomer(request);

        return new ResponseEntity<>(customer, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable long id) {
        Customer customer = customerService.getCustomer(id);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
