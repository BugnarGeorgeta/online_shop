package org.fasttrackit.online_shop;


import org.fasttrackit.online_shop.domain.Customer;
import org.fasttrackit.online_shop.domain.Product;
import org.fasttrackit.online_shop.service.CartService;
import org.fasttrackit.online_shop.steps.CustomerTestSteps;
import org.fasttrackit.online_shop.steps.ProductTestSteps;
import org.fasttrackit.online_shop.transfer.cart.AddProductsToCartRequest;
import org.fasttrackit.online_shop.transfer.cart.CartResponse;
import org.fasttrackit.online_shop.transfer.cart.ProductInCartResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
public class CartServiceIntegrationTests {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerTestSteps customerTestSteps;
    @Autowired
    private ProductTestSteps productTestSteps;

    @Test
    void addProductsToCart_whenNewCart_thenCartIsCreated() {
        Customer customer = customerTestSteps.createCustomer();
        Product product = productTestSteps.createProduct();

        AddProductsToCartRequest request = new AddProductsToCartRequest();
        request.setCustomerId(customer.getId());
        request.setProductIds(Collections.singletonList(product.getId()));

        cartService.addProductsToCart(request);

        CartResponse cart = cartService.getCart(customer.getId());

        assertThat(cart, notNullValue());
        assertThat(cart.getId(), is(customer.getId()));
        assertThat(cart.getProducts(), notNullValue());
        assertThat(cart.getProducts(), hasSize(1));

        Iterator<ProductInCartResponse> productIterator = cart.getProducts().iterator();

        assertThat(productIterator.hasNext(), is(true));
        ProductInCartResponse nextProduct = productIterator.next();

        assertThat(nextProduct, notNullValue());
        assertThat(nextProduct.getId(), is(product.getId()));
        assertThat(nextProduct.getName(), is(product.getName()));
        assertThat(nextProduct.getPrice(), is(product.getPrice()));

    }


}
