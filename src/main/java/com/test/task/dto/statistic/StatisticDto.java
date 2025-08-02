package com.test.task.dto.statistic;

public record StatisticDto(String firstName, String lastName,
                            int amountOfArticles, String email,
                            Long userId) {}
