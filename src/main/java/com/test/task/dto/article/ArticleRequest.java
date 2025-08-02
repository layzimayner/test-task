package com.test.task.dto.article;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public record ArticleRequest(@Max(100) @NotBlank String title,
                             @NotBlank String content) {
}
