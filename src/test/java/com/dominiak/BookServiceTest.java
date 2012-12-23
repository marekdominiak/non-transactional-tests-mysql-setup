package com.dominiak;


import com.dominiak.model.Book;
import com.dominiak.service.BookService;
import org.hibernate.LazyInitializationException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class BookServiceTest extends BaseJpaSpringContextTest {

    @Resource
    private BookService bookService;

    private ExecutorService executorService;

    private Book findAnyExistingBook() {
        return bookService.listBooks(new PageRequest(0, 1)).getContent().get(0);
    }


    @Before
    public void setUp() {
        bookService.addBestSellers();
        executorService = Executors.newFixedThreadPool(2);
    }

    @After
    public void destroy() {
        executorService.shutdownNow();
    }

    @org.junit.Test
    public void shouldThrowLazyInitializationExceptionWhenFetchingLazyOneToManyRelationship() {
        Book someBook = findAnyExistingBook();
        try {
            someBook.getReviews().size();
            Assert.fail();
        } catch (LazyInitializationException e) {

        }
    }

    @org.junit.Test
    public void externalThreadShouldSeeChangesMadeInMainThread() throws Exception {
        final Book someBook = findAnyExistingBook();
        someBook.setAuthor("Bruce Wayne");
        bookService.save(someBook);
        Book foundBook = executorService.submit(new Callable<Book>() {
            @Override
            public Book call() throws Exception {
                return bookService.findBy(someBook.getId());
            }
        }).get();
        Assert.assertEquals(foundBook.getAuthor(), "Bruce Wayne");
    }

    @org.junit.Test
    public void shouldSaveBookToDatabase() throws Exception {
        assertThatNoOfBooksInDatabaseIs(43);
        final Book someBook = new Book();
        someBook.setAuthor("Naughty Bear");
        bookService.save(someBook);
        assertThatNoOfBooksInDatabaseIs(44);
    }

    @org.junit.Test
    public void shouldSaveBookToDatabase_2() throws Exception {
        shouldSaveBookToDatabase();
    }

    private void assertThatNoOfBooksInDatabaseIs(int noOfBooksInDatabase) {
        List<Book> books = bookService.listBooks(new PageRequest(0, 10000)).getContent();
        Assert.assertEquals(books.size(), noOfBooksInDatabase);
    }
}

