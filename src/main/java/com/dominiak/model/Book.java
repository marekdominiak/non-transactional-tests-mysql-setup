package com.dominiak.model;

import com.google.common.collect.Sets;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: marekdominiak
 * Date: 12/18/12
 * Time: 7:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Book {
    private String title;
    private String author;
    private int publishedYear;

    @Id
    @GeneratedValue
    private int id = 0;

    @OneToMany(mappedBy = "book")
    private Set<Review> reviews = Sets.newHashSet();

    public Book(String title, String author, int publishedYear) {
    }

    public Book() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public Set<Review> getReviews() {
        return reviews;
    }


    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }
}
