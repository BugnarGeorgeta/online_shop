package org.fasttrackit.online_shop.web;

import org.fasttrackit.online_shop.domain.Product;
import org.fasttrackit.online_shop.service.ProductService;
import org.fasttrackit.online_shop.transfer.product.GetProductsRequest;
import org.fasttrackit.online_shop.transfer.product.SaveProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Validated SaveProductRequest request) {
        Product product = productService.createProduct(request);

        return new ResponseEntity<>(product, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody @Validated SaveProductRequest request) {
        Product updateProduct = productService.updateProduct(id, request);

        return new ResponseEntity<>(updateProduct, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id) {
        Product product = productService.getProduct(id);

        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Page<Product>> getProducts
            (GetProductsRequest request, Pageable pageable) {
        Page<Product> products = productService.getProducts(request, pageable);

        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}

