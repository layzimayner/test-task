package com.test.task.dto.article;

import java.time.LocalDateTime;

public record ArticleDto(Long articleId,
                         String title,
                         String content,
                         LocalDateTime dateOfPublishing) {
}
