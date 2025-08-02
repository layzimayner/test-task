package com.wrap.it.service;

import com.test.task.dto.article.ArticleDto;
import com.test.task.dto.article.ArticleRequest;
import com.test.task.exception.EntityNotFoundException;
import com.test.task.exception.NotYourArticleException;
import com.test.task.mapper.ArticleMapper;
import com.test.task.model.Article;
import com.test.task.model.User;
import com.test.task.repository.ArticleRepository;
import com.test.task.service.impl.ArticleServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {
    private static final Long DEFAULT_ARTICLE_ID = 1L;
    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long ALTER_USER_ID = 2L;
    private static final String DEFAULT_ARTICLE_TITLE = "test title";
    private static final String DEFAULT_ARTICLE_CONTENT = "test content";
    private static final LocalDateTime DEFAULT_ARTICLE_DATE_OF_PUBLISHING =
            LocalDateTime.now();
    private static final Pageable TEST_PAGEABLE = PageRequest.of(0, 10);;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleMapper articleMapper;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    @DisplayName("Check saving service with valid request")
    void save_ValidRequest_ReturnDto() {
        //Given
        Article model = createModel();
        ArticleDto expected = createDto();
        ArticleRequest request = createRequest();

        when(articleRepository.save(model)).thenReturn(model);
        when(articleMapper.toModel(eq(request),
                any(User.class),
                any(LocalDateTime.class)))
                .thenReturn(model);
        when(articleMapper.toDto(model)).thenReturn(expected);

        //When
        ArticleDto actual = articleService.save(request, creteUser());

        //Then
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Check findAll service with valid request")
    void findAll_ValidRequest_ReturnPage() {
        //Given
        Article model = createModel();
        Page<Article> page = new PageImpl<>(List.of((model)));
        ArticleDto expect = createDto();

        when(articleRepository.findAll(TEST_PAGEABLE)).thenReturn(page);
        when(articleMapper.toDto(model)).thenReturn(expect);

        //When
        Page<ArticleDto> actual = articleService.findAll(TEST_PAGEABLE);

        //Then
        Assertions.assertEquals(actual, new PageImpl<>(List.of(expect)));
    }

    @Test
    @DisplayName("Check delete service with valid request")
    void delete_ValidRequest_ReturnDto() {
        //Given
        Article model = createModel();

        when(articleRepository.findByIdWithUser(DEFAULT_ARTICLE_ID)).thenReturn(Optional.of(model));
        doNothing().when(articleRepository).delete(model);

        //When
        Assertions.assertDoesNotThrow(() -> articleService.delete(DEFAULT_ARTICLE_ID, creteUser()));

        //Then
        verify(articleRepository, times(1)).delete(model);
    }

    @Test
    @DisplayName("Check delete service with not existing article id")
    void delete_NotExistingId_ThrowException() {
        //Given
        when(articleRepository.findByIdWithUser(DEFAULT_ARTICLE_ID)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class,
                () -> articleService.delete(DEFAULT_ARTICLE_ID, creteUser()));

        verify(articleRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Check delete service with out rights on delete")
    void delete_NotYourArticle_ThrowException() {
        //Given
        Article model = createModel();
        User user = creteUser();
        user.setId(ALTER_USER_ID);
        model.setUser(user);

        when(articleRepository.findByIdWithUser(DEFAULT_ARTICLE_ID)).thenReturn(Optional.of(model));

        // When & Then
        assertThrows(NotYourArticleException.class,
                () -> articleService.delete(DEFAULT_ARTICLE_ID, creteUser()));

        verify(articleRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Check findById service with valid request")
    void findById_ValidRequest_returnDto() {
        //Given
        Article model = createModel();
        ArticleDto expected = createDto();

        when(articleRepository.findById(DEFAULT_ARTICLE_ID)).thenReturn(Optional.of(model));
        when(articleMapper.toDto(model)).thenReturn(expected);

        //When
        ArticleDto actual = articleService.findById(DEFAULT_ARTICLE_ID);

        //Then
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Check findById service with not real id")
    void findById_NotExistingId_ThrowException() {
        //Given
        ArticleDto expected = createDto();

        when(articleRepository.findById(DEFAULT_ARTICLE_ID)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class,
                () -> articleService.findById(DEFAULT_ARTICLE_ID));
    }

    @Test
    @DisplayName("Check update service with valid request")
    void update_ValidRequest_returnDto() {
        //Given
        Article model = createModel();
        ArticleDto expect = createDto();
        ArticleRequest request = createRequest();

        when(articleRepository.findByIdWithUser(DEFAULT_ARTICLE_ID)).thenReturn(Optional.of(model));
        doNothing().when(articleMapper).update(any(), any(), any());
        when(articleRepository.save(model)).thenReturn(model);
        when(articleMapper.toDto(model)).thenReturn(expect);

        //When
        ArticleDto actual = articleService.update(DEFAULT_ARTICLE_ID, request, creteUser());

        //Then
        Assertions.assertEquals(actual, expect);
    }

    @Test
    @DisplayName("Check update service with not real id")
    void update_NotExistingId_ThrowException() {
        //Given
        ArticleRequest request = createRequest();
        when(articleRepository.findByIdWithUser(DEFAULT_ARTICLE_ID)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class,
                () -> articleService.update(DEFAULT_ARTICLE_ID, request, creteUser()));
    }

    @Test
    @DisplayName("Check update service with not your article")
    void update_NotYourArticle_ThrowException() {
        //Given
        Article model = createModel();

        User user = creteUser();
        user.setId(ALTER_USER_ID);
        model.setUser(user);

        ArticleRequest request = createRequest();

        when(articleRepository.findByIdWithUser(DEFAULT_ARTICLE_ID)).thenReturn(Optional.of(model));

        // When & Then
        assertThrows(NotYourArticleException.class,
                () -> articleService.update(DEFAULT_ARTICLE_ID, request, creteUser()));
    }

    private ArticleRequest createRequest() {
        return new ArticleRequest(DEFAULT_ARTICLE_TITLE,
                DEFAULT_ARTICLE_CONTENT);
    }

    private Article createModel() {
        Article article = new Article();
        article.setId(DEFAULT_ARTICLE_ID);
        article.setTitle(DEFAULT_ARTICLE_TITLE);
        article.setContent(DEFAULT_ARTICLE_CONTENT);
        article.setUser(creteUser());
        article.setDateOfPublishing(DEFAULT_ARTICLE_DATE_OF_PUBLISHING);
        return article;
    }

    private ArticleDto createDto() {
        return new ArticleDto(DEFAULT_ARTICLE_ID,
                DEFAULT_ARTICLE_TITLE,
                DEFAULT_ARTICLE_CONTENT,
                DEFAULT_ARTICLE_DATE_OF_PUBLISHING);
    }

    private User creteUser() {
        User user = new User();
        user.setId(DEFAULT_USER_ID);
        return user;
    }

}
