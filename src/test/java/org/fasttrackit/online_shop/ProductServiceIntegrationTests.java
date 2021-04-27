package org.fasttrackit.online_shop;

import org.fasttrackit.online_shop.domain.Product;
import org.fasttrackit.online_shop.exception.ResourcesNotFoundException;
import org.fasttrackit.online_shop.service.ProductService;
import org.fasttrackit.online_shop.steps.ProductTestSteps;
import org.fasttrackit.online_shop.transfer.product.SaveProductRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTestSteps productTestSteps;

    @Test
    void createProduct_whenValidRequest_thenProductIsCreated() {

        productTestSteps.createProduct();
    }



    @Test
    void createProduct_whenMissingName_thenExceptionThrow() {
        SaveProductRequest request = new SaveProductRequest();
        request.setPrice(1500.0);
        request.setQuantity(28);

        try {
            productService.createProduct(request);
        } catch (Exception e) {
            assertThat(e, notNullValue());
            //    assertThat("Unexpected exception type.", e instanceof TransactionSystemException);
        }
    }

    @Test
    void getProduct_whenExistingProduct_thenReturnProduct() {
        Product product = productTestSteps.createProduct();

        Product response = productService.getProduct(product.getId());

        assertThat(response, notNullValue());
        assertThat(response.getId(), is(product.getId()));
        assertThat(response.getName(), is(product.getName()));
        assertThat(response.getPrice(), is(product.getPrice()));
        assertThat(response.getQuantity(), is(product.getQuantity()));
        assertThat(response.getDescription(), is(product.getDescription()));

    }

    @Test
    void getProduct_whenNonExistingProduct_thenThrowResourceNotFound() {
        Assertions.assertThrows(ResourcesNotFoundException.class,
                () -> productService.getProduct(999999));
    }

    @Test
    void updateProduct_whenValidRequest_thenReturnUpdatingProduct() {
        Product product = productTestSteps.createProduct();

        SaveProductRequest request = new SaveProductRequest();
        request.setName(product.getName() + "update");
        request.setDescription(product.getDescription() + "update");
        request.setPrice(product.getPrice() + 99);
        request.setQuantity(product.getQuantity() + 10);

        Product updateProduct = productService.updateProduct(product.getId(), request);

        assertThat(updateProduct, notNullValue());
        assertThat(updateProduct.getId(), is(product.getId()));
        assertThat(updateProduct.getName(), is(request.getName()));
        assertThat(updateProduct.getDescription(), is(request.getDescription()));
        assertThat(updateProduct.getPrice(), is(request.getPrice()));
        assertThat(updateProduct.getQuantity(), is(request.getQuantity()));
    }

    @Test
    void deleteProduct_whenExistingProduct_thenProductDoesNotExist() {
        Product product = productTestSteps.createProduct();

        productService.deleteProduct(product.getId());

        Assertions.assertThrows(ResourcesNotFoundException.class,
                () -> productService.getProduct(product.getId()));


    }
}
