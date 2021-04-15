package org.fasttrackit.online_shop;

import org.fasttrackit.online_shop.domain.Customer;
import org.fasttrackit.online_shop.service.CartService;
import org.fasttrackit.online_shop.steps.CustomerTestSteps;
import org.fasttrackit.online_shop.transfer.cart.AddProductsToCartRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartServiceIntegrationTests {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerTestSteps customerTestSteps;

    @Test
    void addProductsToCart_whenNewCart_thenCartIsCreated() {
        Customer customer = customerTestSteps.createCustomer();

        AddProductsToCartRequest request = new AddProductsToCartRequest();
        request.setCustomerId(customer.getId());

        cartService.addProductsToCart(request);

    }
}
