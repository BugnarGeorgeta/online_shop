package org.fasttrackit.online_shop.service;

import org.fasttrackit.online_shop.domain.Product;
import org.fasttrackit.online_shop.exception.ResourcesNotFoundException;
import org.fasttrackit.online_shop.persistance.ProductRepository;
import org.fasttrackit.online_shop.transfer.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    //IoC
    public final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    public Product createProduct(SaveProductRequest request) {
        LOGGER.info("Creating product {} ", request);

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        return productRepository.save(product);

    }

    public Product getProduct(long id) {
        LOGGER.info("Retrieving product {}", id);
//        Optional<Product> productOptional = productRepository.findById(id);
//        if (productOptional.isPresent()) {
//            return productOptional.get();
//        } else {
//            throw new ResourcesNotFoundException("Product " + id + " not found.");
//        }
      // lambda expression
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Product " + id + "not found."));
    }
}