package org.fasttrackit.online_shop.service;

import org.fasttrackit.online_shop.domain.Cart;
import org.fasttrackit.online_shop.domain.Customer;
import org.fasttrackit.online_shop.domain.Product;
import org.fasttrackit.online_shop.exception.ResourcesNotFoundException;
import org.fasttrackit.online_shop.persistance.CartRepository;
import org.fasttrackit.online_shop.transfer.cart.AddProductsToCartRequest;
import org.fasttrackit.online_shop.transfer.cart.CartResponse;
import org.fasttrackit.online_shop.transfer.cart.ProductInCartResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import java.util.Set;

@Service
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, CustomerService customerService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.customerService = customerService;
        this.productService = productService;
    }
   @Transactional
    public void addProductsToCart(AddProductsToCartRequest request) {
        LOGGER.info("Adding products to cart: {}", request);

        Cart cart = cartRepository.findById(request.getCustomerId()).
                orElse(new Cart());

        if (cart.getCustomer() == null) {
            Customer customer = customerService.getCustomer(request.getCustomerId());

            cart.setCustomer(customer);

        }
        for(Long id: request.getProductIds()) {
            Product product = productService.getProduct(id);
            cart.addProductToCart(product);
        }
        cartRepository.save(cart);

    }
   @Transactional
    public CartResponse getCart(long customerId){
        LOGGER.info("Retrieving cart items for customer {}", customerId);

        Cart cart = cartRepository.findById(customerId).
                orElseThrow(() -> new ResourcesNotFoundException("Cart" + customerId +
                        " is not exist"));
       CartResponse cartResponse= new CartResponse();
       cartResponse.setId(cart.getId());

        Set<ProductInCartResponse> productDtos= new HashSet<>();
       for (Product nextProduct : cart.getProducts()) {
           ProductInCartResponse productDto = new ProductInCartResponse();
           productDto.setId(nextProduct.getId());
           productDto.setName(nextProduct.getName());
           productDto.setPrice(nextProduct.getPrice());

           productDtos.add(productDto);
       }
        return cartResponse;
    }
}
