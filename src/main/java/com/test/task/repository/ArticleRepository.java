package com.test.task.repository;

import com.test.task.model.Article;
import com.test.task.model.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("""
        SELECT a.user
        FROM Article a
        WHERE a.dateOfPublishing >= :fromDate
        GROUP BY a.user
        ORDER BY COUNT(a) DESC
            """)
    List<User> findTopAuthors(LocalDateTime fromDate);

    @Query("SELECT COUNT(a) as articleCount "
            + "FROM Article a "
            + "WHERE a.dateOfPublishing >= :fromDate "
            + "GROUP BY a.user.id "
            + "ORDER BY articleCount DESC")
    List<Long> countArticlesForUsersSince(LocalDateTime fromDate);

    @Query("SELECT a FROM Article a JOIN FETCH a.user u WHERE a.id = :articleId")
    Optional<Article> findByIdWithUser(Long articleId);
}
