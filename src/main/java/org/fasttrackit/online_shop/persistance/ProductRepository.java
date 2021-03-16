package org.fasttrackit.online_shop.persistance;

import org.fasttrackit.online_shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
