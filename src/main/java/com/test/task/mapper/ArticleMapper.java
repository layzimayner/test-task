package com.test.task.mapper;

import com.test.task.config.MapperConfig;
import com.test.task.dto.article.ArticleDto;
import com.test.task.dto.article.ArticleRequest;
import com.test.task.model.Article;
import com.test.task.model.User;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface ArticleMapper {
    @Mapping(target = "articleId", source = "article.id")
    ArticleDto toDto(Article article);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfPublishing", source = "today")
    Article toModel(ArticleRequest request, User user, LocalDateTime today);

    @Mapping(target = "dateOfPublishing", source = "today")
    void update(@MappingTarget Article article,
                ArticleRequest requestDto,
                LocalDateTime today);
}
