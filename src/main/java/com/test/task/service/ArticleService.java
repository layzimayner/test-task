package com.test.task.service;

import com.test.task.dto.article.ArticleDto;
import com.test.task.dto.article.ArticleRequest;
import com.test.task.model.User;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    ArticleDto save(ArticleRequest request, User user);

    Page<ArticleDto> findAll(Pageable pageable);

    ArticleDto delete(@Positive Long articleId, User user);

    ArticleDto update(@Positive Long articleId, ArticleRequest requestDto, User user);

    ArticleDto findById(@Positive Long articleId);
}
