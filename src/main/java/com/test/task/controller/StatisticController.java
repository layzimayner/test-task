package com.test.task.controller;

import com.test.task.dto.statistic.StatisticDto;
import com.test.task.service.StatisticService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "statistic management", description = "Endpoints for statistic gathering")
@RestController
@RequestMapping("/statistic")
@Validated
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<StatisticDto> getStatistic(@RequestParam(
            required = false, defaultValue = "3")int numOfAuthors) {
        return statisticService.getStatistic(numOfAuthors);
    }
}
