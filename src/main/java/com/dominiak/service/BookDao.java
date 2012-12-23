package com.dominiak.service;

import com.dominiak.model.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract interface BookDao extends PagingAndSortingRepository<Book, Integer> {
}
