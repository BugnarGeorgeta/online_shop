package org.fasttrackit.online_shop.persistance;

import org.fasttrackit.online_shop.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
