package com.test.task.service;

import com.test.task.dto.statistic.StatisticDto;
import java.util.List;

public interface StatisticService {
    List<StatisticDto> getStatistic(int numOfAuthors);
}
