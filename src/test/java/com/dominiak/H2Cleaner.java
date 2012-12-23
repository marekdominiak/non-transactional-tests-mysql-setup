package com.dominiak;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

@Configuration
@Profile("h2")
public class H2Cleaner implements DatabaseCleaner {
    private File dbScriptFile;

    @Inject
    DataSource dataSource;

    public H2Cleaner() throws IOException {
        dbScriptFile = File.createTempFile(getClass().getSimpleName() + "-", ".sql");
    }

    @Override
    public void beforeTest() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("SCRIPT NOPASSWORDS DROP TO '" + dbScriptFile.getPath() + "'");
        dbScriptFile.deleteOnExit();
    }

    @Override
    public void afterTest() {
        new JdbcTemplate(dataSource).execute("RUNSCRIPT FROM '" + dbScriptFile.getPath() + "'");
    }
}
