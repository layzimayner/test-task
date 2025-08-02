package com.wrap.it.service;

import com.test.task.dto.article.ArticleDto;
import com.test.task.dto.article.ArticleRequest;
import com.test.task.dto.statistic.StatisticDto;
import com.test.task.mapper.StatisticMapper;
import com.test.task.model.Article;
import com.test.task.model.Role;
import com.test.task.model.User;
import com.test.task.repository.ArticleRepository;
import com.test.task.service.impl.StatisticServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatisticServiceTest {
    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();
    private static final String DEFAULT_USER_EMAIL = "user@mail.com";
    private static final String DEFAULT_USER_FIRST_NAME = "user";
    private static final String DEFAULT_USER_LAST_NAME = "usersun";
    private static final int FIRST_PIECE_OF_STATISTIC = 1;
    private static final int SECOND_PIECE_OF_STATISTIC = 2;
    private static final int THIRD_PIECE_OF_STATISTIC = 3;
    private static final int NUMBER_OF_AUTHOR_FOR_GATHERING_STATISTIC = 3;
    private static final int LARGE_NUMBER_OF_AUTHOR_FOR_GATHERING_STATISTIC = 10;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private StatisticMapper statisticMapper;

    @InjectMocks
    private StatisticServiceImpl statisticService;

    @Test
    @DisplayName("Check get statistic service with valid request")
    void getStatistic_ValidRequest_ReturnList() {
        //Given
        List<User> topUsers = creteUsers();
        List<Long> topCountOfArticlesByUser = List.of((long) FIRST_PIECE_OF_STATISTIC,
                (long) SECOND_PIECE_OF_STATISTIC,
                (long) THIRD_PIECE_OF_STATISTIC);

        when(articleRepository.findTopAuthors(any())).thenReturn(topUsers);

        when(articleRepository.countArticlesForUsersSince(any()))
                .thenReturn(topCountOfArticlesByUser);

        when(statisticMapper.toDto(createUser(FIRST_PIECE_OF_STATISTIC),
                        (long)FIRST_PIECE_OF_STATISTIC))
                .thenReturn(createDto(FIRST_PIECE_OF_STATISTIC));

        when(statisticMapper.toDto(createUser(SECOND_PIECE_OF_STATISTIC),
                (long) SECOND_PIECE_OF_STATISTIC))
                .thenReturn(createDto(SECOND_PIECE_OF_STATISTIC));

        when(statisticMapper.toDto(createUser(THIRD_PIECE_OF_STATISTIC),
                (long) THIRD_PIECE_OF_STATISTIC))
                .thenReturn(createDto(THIRD_PIECE_OF_STATISTIC));

        //When
        List<StatisticDto> actual = statisticService
                .getStatistic(NUMBER_OF_AUTHOR_FOR_GATHERING_STATISTIC);

        //Then
        Assertions.assertEquals(NUMBER_OF_AUTHOR_FOR_GATHERING_STATISTIC, actual.size());
    }

    @Test
    @DisplayName("Check get statistic service with too large amount of authors")
    void getStatistic_TooLargeAmount_ReturnList() {
        //Given
        List<User> topUsers = creteUsers();
        List<Long> topCountOfArticlesByUser = List.of((long) FIRST_PIECE_OF_STATISTIC,
                (long) SECOND_PIECE_OF_STATISTIC,
                (long) THIRD_PIECE_OF_STATISTIC);

        when(articleRepository.findTopAuthors(any())).thenReturn(topUsers);

        when(articleRepository.countArticlesForUsersSince(any()))
                .thenReturn(topCountOfArticlesByUser);

        when(statisticMapper.toDto(createUser(FIRST_PIECE_OF_STATISTIC),
                (long)FIRST_PIECE_OF_STATISTIC))
                .thenReturn(createDto(FIRST_PIECE_OF_STATISTIC));

        when(statisticMapper.toDto(createUser(SECOND_PIECE_OF_STATISTIC),
                (long) SECOND_PIECE_OF_STATISTIC))
                .thenReturn(createDto(SECOND_PIECE_OF_STATISTIC));

        when(statisticMapper.toDto(createUser(THIRD_PIECE_OF_STATISTIC),
                (long) THIRD_PIECE_OF_STATISTIC))
                .thenReturn(createDto(THIRD_PIECE_OF_STATISTIC));

        //When
        List<StatisticDto> actual = statisticService
                .getStatistic(LARGE_NUMBER_OF_AUTHOR_FOR_GATHERING_STATISTIC);

        //Then
        Assertions.assertEquals(NUMBER_OF_AUTHOR_FOR_GATHERING_STATISTIC, actual.size());
    }

    private List<User> creteUsers() {
        User user1 = createUser(FIRST_PIECE_OF_STATISTIC);
        User user2 = createUser(SECOND_PIECE_OF_STATISTIC);
        User user3 = createUser(THIRD_PIECE_OF_STATISTIC);
        return List.of(user1, user2, user3);
    }

    private User createUser(int num) {
        User user = new User();
        user.setId((long) num);
        user.setEmail(DEFAULT_USER_EMAIL + num);
        user.setFirstName(DEFAULT_USER_FIRST_NAME + num);
        user.setLastName(DEFAULT_USER_LAST_NAME + num);
        return user;

    }

    private StatisticDto createDto(int num) {

        return new StatisticDto(
                DEFAULT_USER_FIRST_NAME + num,
                DEFAULT_USER_LAST_NAME + num,
                num,
                DEFAULT_USER_EMAIL,
                (long)num
        );
    }
}
