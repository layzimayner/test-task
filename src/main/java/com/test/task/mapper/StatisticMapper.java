package com.test.task.mapper;

import com.test.task.config.MapperConfig;
import com.test.task.dto.statistic.StatisticDto;
import com.test.task.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface StatisticMapper {
    @Mapping(source = "author.id", target = "userId")
    StatisticDto toDto(User author, Long amountOfArticles);
}
