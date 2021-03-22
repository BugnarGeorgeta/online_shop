package org.fasttrackit.online_shop.service;

import org.fasttrackit.online_shop.domain.Product;
import org.fasttrackit.online_shop.exception.ResourcesNotFoundException;
import org.fasttrackit.online_shop.persistance.ProductRepository;
import org.fasttrackit.online_shop.transfer.product.GetProductsRequest;
import org.fasttrackit.online_shop.transfer.product.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    //IoC
    private final ProductRepository productRepository;

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

    public Page<Product> getProducts(GetProductsRequest request, Pageable pageable) {
        LOGGER.info("Searching products {} ", request);

        if (request != null) {
            if (request.getPartialName() != null && request.getMinQuantity() != null) {
                productRepository.findByNameContainingAndQuantityGreaterThanEqual(
                        request.getPartialName(), request.getMinQuantity(), pageable);
            } else if (request.getPartialName() != null) {
                productRepository.findByNameContaining(request.getPartialName(), pageable);
            }
        }
        return productRepository.findAll(pageable);

    }

    public Product updateProduct(long id, SaveProductRequest request) {
        LOGGER.info("Updating product {}: {}", id, request);

        Product product = getProduct(id);
        BeanUtils.copyProperties(request, product);

        return productRepository.save(product);

    }

    public void deleteProduct(long id) {
        LOGGER.info("Deleting product {}", id);
        productRepository.deleteById(id);
    }


}