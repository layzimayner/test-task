package com.test.task.controller;

import com.test.task.dto.article.ArticleDto;
import com.test.task.dto.article.ArticleRequest;
import com.test.task.model.User;
import com.test.task.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "articles management", description = "Endpoints for management articles")
@RestController
@RequestMapping("/articles")
@Validated
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    @Operation(summary = "Post an article", description = "Add new article to DB")
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleDto createArticle(@RequestBody @Valid ArticleRequest request,
                                    Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return articleService.save(request, user);
    }

    @GetMapping
    @Operation(summary = "Get all articles", description = "Get a page of all available articles")
    public Page<ArticleDto> getAllArticles(Pageable pageable) {
        return articleService.findAll(pageable);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete article", description = "Delete article by id")
    public ArticleDto deleteItem(@PathVariable @Positive Long id,
                                 Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return articleService.delete(id, user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update article", description = "Update article by id")
    public ArticleDto updateItem(@PathVariable @Positive Long id,
                              @RequestBody @Valid ArticleRequest requestDto,
                                 Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return articleService.update(id, requestDto, user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get article by id", description = "Get article by id")
    public ArticleDto getItemById(@PathVariable @Positive Long id) {
        return articleService.findById(id);
    }
}
