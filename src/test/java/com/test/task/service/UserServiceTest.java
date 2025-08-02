package com.wrap.it.service;

import com.test.task.dto.article.ArticleDto;
import com.test.task.dto.article.ArticleRequest;
import com.test.task.dto.user.UserRegistrationDto;
import com.test.task.dto.user.UserRegistrationRequestDto;
import com.test.task.exception.EntityNotFoundException;
import com.test.task.exception.RegistrationException;
import com.test.task.mapper.UserMapper;
import com.test.task.model.Article;
import com.test.task.model.Role;
import com.test.task.model.User;
import com.test.task.repository.RoleRepository;
import com.test.task.repository.UserRepository;
import com.test.task.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static final Long DEFAULT_USER_ID = 1L;
    private static final String DEFAULT_USER_EMAIL = "user@mail.com";
    private static final String DEFAULT_USER_FIRST_NAME = "user";
    private static final String DEFAULT_USER_LAST_NAME = "usersun";
    private static final String DEFAULT_USER_PASSWORD = "user";
    private static final Role.RoleName DEFAULT_USER_ROLE_NAME = Role.RoleName.USER;

    @Mock
    private  UserRepository userRepository;

    @Mock
    private  RoleRepository roleRepository;

    @Mock
    private  UserMapper userMapper;

    @Mock
    private  PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Check saving service with occupied email")
    void save_OccupiedEmail_ThrowException() {
        //Given
        User model = createModel();

        when(userRepository.existsByEmail(DEFAULT_USER_EMAIL)).thenReturn(true);

        //When & //Then
        assertThrows(RegistrationException.class,
                () -> userService.save(createRequest()));

        verify(userRepository, never()).save(any());

    }

    @Test
    @DisplayName("Check saving service with valid request")
    void save_ValidRequest_ReturnDto() {
        //Given
        User model = createModel();
        UserRegistrationRequestDto request = createRequest();
        UserRegistrationDto expected = createDto();

        when(userRepository.existsByEmail(DEFAULT_USER_EMAIL)).thenReturn(false);
        when(userMapper.toModel(request)).thenReturn(model);
        when(roleRepository.findByName(DEFAULT_USER_ROLE_NAME)).thenReturn(createRole());
        when(passwordEncoder.encode(DEFAULT_USER_PASSWORD)).thenReturn(DEFAULT_USER_PASSWORD);
        when(userRepository.save(model)).thenReturn(model);
        when(userMapper.toDto(model)).thenReturn(expected);

        //When
        UserRegistrationDto actual = null;
        try {
            actual = userService.save(request);
        } catch (RegistrationException e) {
            throw new RuntimeException("Exception duri"
                    + "ng save user test case with request " + request , e);
        }

        //Then
        Assertions.assertEquals(actual, expected);

    }

    private UserRegistrationDto createDto() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setEmail(DEFAULT_USER_EMAIL);
        dto.setFirstName(DEFAULT_USER_FIRST_NAME);
        dto.setLastName(DEFAULT_USER_LAST_NAME);
        dto.setId(DEFAULT_USER_ID);
        return dto;
    }

    private User createModel() {
        User user = new User();
        user.setId(DEFAULT_USER_ID);
        user.setEmail(DEFAULT_USER_EMAIL);
        user.setFirstName(DEFAULT_USER_FIRST_NAME);
        user.setLastName(DEFAULT_USER_LAST_NAME);
        return user;
    }

    private UserRegistrationRequestDto createRequest() {
        UserRegistrationRequestDto request = new UserRegistrationRequestDto();
        request.setEmail(DEFAULT_USER_EMAIL);
        request.setFirstName(DEFAULT_USER_FIRST_NAME);
        request.setLastName(DEFAULT_USER_LAST_NAME);
        request.setPassword(DEFAULT_USER_PASSWORD);
        return request;
    }

    private Role createRole() {
        return new Role(DEFAULT_USER_ID, DEFAULT_USER_ROLE_NAME);
    }
}
