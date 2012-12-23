package com.dominiak.service;

import com.dominiak.model.Review;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract interface ReviewDao extends PagingAndSortingRepository<Review, Integer> {
}
