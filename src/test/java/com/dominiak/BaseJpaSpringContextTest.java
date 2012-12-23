package com.dominiak;

import org.junit.After;
import org.junit.Before;

import javax.inject.Inject;

public class BaseJpaSpringContextTest {

    static {
        System.setProperty("spring.profiles.default", "h2");
    }

    @Inject
    private DatabaseCleaner databaseCleaner;

    @Before
    public void beforeAll() {
        databaseCleaner.beforeTest();
    }

    @After
    public void afterEach() {
        databaseCleaner.afterTest();
    }
}
