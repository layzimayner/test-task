package com.test.task.service.impl;

import com.test.task.dto.article.ArticleDto;
import com.test.task.dto.article.ArticleRequest;
import com.test.task.exception.EntityNotFoundException;
import com.test.task.exception.NotYourArticleException;
import com.test.task.mapper.ArticleMapper;
import com.test.task.model.Article;
import com.test.task.model.User;
import com.test.task.repository.ArticleRepository;
import com.test.task.service.ArticleService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    public ArticleDto save(ArticleRequest request, User user) {

        LocalDateTime today = LocalDateTime.now();

        Article article = articleMapper.toModel(request, user, today);

        return articleMapper.toDto(articleRepository.save(article));
    }

    @Override
    public Page<ArticleDto> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable)
                .map(articleMapper::toDto);
    }

    @Override
    public ArticleDto delete(Long articleId, User user) {

        Article article = articleRepository.findByIdWithUser(articleId).orElseThrow(() ->
                new EntityNotFoundException("Article with id: " + articleId
                    + " does not exist"));

        if (article.getUser().equals(user)) {
            ArticleDto dto = articleMapper.toDto(article);

            articleRepository.delete(article);

            return dto;
        } else {
            throw new NotYourArticleException("You can't delete this article");
        }
    }

    @Override
    public ArticleDto update(Long articleId, ArticleRequest requestDto, User user) {

        Article article = articleRepository.findByIdWithUser(articleId).orElseThrow(() ->
                new EntityNotFoundException("Article with id: " + articleId
                        + " does not exist"));

        if (article.getUser().equals(user)) {

            LocalDateTime today = LocalDateTime.now();

            articleMapper.update(article, requestDto, today);

            return articleMapper.toDto(articleRepository.save(article));

        } else {
            throw new NotYourArticleException("You can't update this article");
        }
    }

    @Override
    public ArticleDto findById(Long articleId) {

        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new EntityNotFoundException("Article with id: " + articleId
                        + "does not exist"));

        return articleMapper.toDto(article);
    }
}
