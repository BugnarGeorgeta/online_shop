package org.fasttrackit.online_shop.service;

import org.fasttrackit.online_shop.domain.Review;
import org.fasttrackit.online_shop.persistance.ReviewRepository;
import org.fasttrackit.online_shop.transfer.review.ReviewResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewService.class);

    private final ReviewRepository repository;

    @Autowired
    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Page<ReviewResponse> getReviews(long productId, Pageable pageable) {
        LOGGER.info("Retrieving reviews for product {}", productId);

        Page<Review> reviewsPage = repository.findByProductId(productId, pageable);

        List<ReviewResponse> reviewDtos = new ArrayList<>();

        for (Review review : reviewsPage.getContent()) {
            ReviewResponse dto = new ReviewResponse();
            dto.setId(review.getId());
            dto.setContent(review.getContent());

            reviewDtos.add(dto);


        }
        return new PageImpl<>(reviewDtos, pageable, reviewsPage.getTotalElements());

    }

}
