package com.dominiak.service;

import com.dominiak.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class BookServiceNoTx {

    @PersistenceContext
    private EntityManager entityManager;

    public Book saveNoTx(Book book) {
        entityManager.persist(book);
        return book;
    }

}
