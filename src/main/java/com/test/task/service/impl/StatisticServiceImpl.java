package com.test.task.service.impl;

import com.test.task.dto.statistic.StatisticDto;
import com.test.task.mapper.StatisticMapper;
import com.test.task.model.User;
import com.test.task.repository.ArticleRepository;
import com.test.task.service.StatisticService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private static final int STATISTIC_COLLECTION_INTERVAL = 50;

    private final ArticleRepository articleRepository;
    private final StatisticMapper statisticMapper;

    @Override
    public List<StatisticDto> getStatistic(int numOfAuthors) {
        int index = 0;

        List<StatisticDto> statistic = new java.util.ArrayList<>(List.of());
        LocalDateTime fromDate = LocalDateTime.now().minusDays(
                STATISTIC_COLLECTION_INTERVAL);

        List<User> topAuthors = articleRepository.findTopAuthors(fromDate);
        List<Long> topNumbers = articleRepository.countArticlesForUsersSince(fromDate);

        if (topAuthors.size() < numOfAuthors || topNumbers.size() < numOfAuthors) {
            numOfAuthors = topAuthors.size();
        }

        while (numOfAuthors > 0) {
            statistic.add(statisticMapper.toDto(
                    topAuthors.get(index),
                    topNumbers.get(index)));
            numOfAuthors--;
            index++;
        }

        return statistic;
    }
}
