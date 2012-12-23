package com.dominiak.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Review {
    private String author;
    private String contents;

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Book book;

    public Review(String author, String contents, int id, Book book) {
        this.author = author;
        this.contents = contents;
        this.id = id;
        this.book = book;
    }

    public Review() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (id != review.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}