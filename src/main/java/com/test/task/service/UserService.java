package com.test.task.service;

import com.test.task.dto.user.UserRegistrationDto;
import com.test.task.dto.user.UserRegistrationRequestDto;
import com.test.task.exception.RegistrationException;
import jakarta.validation.Valid;

public interface UserService {
    UserRegistrationDto save(
            @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException;
}
