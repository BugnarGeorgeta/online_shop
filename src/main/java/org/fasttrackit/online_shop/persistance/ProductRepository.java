package org.fasttrackit.online_shop.persistance;

import org.fasttrackit.online_shop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product>findByNameContaining(String partialName, Pageable pageable);

    Page<Product>findByNameContainingAndQuantityGreaterThanEqual
            (String partialName, int minQuantity, Pageable pageable);


}
