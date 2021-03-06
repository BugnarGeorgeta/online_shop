package org.fasttrackit.online_shop.persistance;

import org.fasttrackit.online_shop.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByProductId(long productId, Pageable pageable);
}
