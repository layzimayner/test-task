package com.test.task.repository;


import com.test.task.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@Testcontainers
@Sql(scripts = "classpath:scripts/statistic/cleanup-db.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:scripts/statistic/insert-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:scripts/statistic/cleanup-db.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("Check result of findTopAuthors method")
    void findTopAuthors_ReturnTopUsers() {
        LocalDateTime fromDate = LocalDateTime.of(2024, 1, 1, 0, 0);

        List<User> result = articleRepository.findTopAuthors(fromDate);

        Assertions.assertEquals(4, result.size());

        Assertions.assertEquals(1L, result.get(0).getId());
    }

    @Test
    @DisplayName("Check result of countArticlesForUsersSince method")
    void countArticlesForUsersSince_ReturnArticleCounts() {
        LocalDateTime fromDate = LocalDateTime.of(2024, 1, 1, 0, 0);

        List<Long> result = articleRepository.countArticlesForUsersSince(fromDate);

        Assertions.assertEquals(4, result.size());

        Assertions.assertEquals(5L, result.get(0));
    }
}
