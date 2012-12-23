package com.dominiak;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Configuration
@Profile("mysql")
public class MysqlCleaner implements DatabaseCleaner {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private PlatformTransactionManager transactionManager;

    @Override
    public void beforeTest() {
        // nothing to do here
    }

    @Override
    public void afterTest() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                disableForeignKeysInCurrentTranaction();
                deleteDataFromAllTables();
                enableForeignKeysInCurrentTransaction();
            }
        });

    }

    private void enableForeignKeysInCurrentTransaction() {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1")
                .executeUpdate();
    }

    @SuppressWarnings("unchecked")
    private void deleteDataFromAllTables() {
        List<String> tablesInDatabase = entityManager.createNativeQuery("show tables")
                .getResultList();
        for (String tableName : tablesInDatabase) {
            deleteDataFrom(tableName);
        }
    }

    private void deleteDataFrom(String tableName) {
        entityManager.createNativeQuery("delete from " + tableName)
                .executeUpdate();
    }

    private void disableForeignKeysInCurrentTranaction() {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0")
                .executeUpdate();
    }
}
