package com.test.task.mapper;

import com.test.task.config.MapperConfig;
import com.test.task.dto.user.UserRegistrationDto;
import com.test.task.dto.user.UserRegistrationRequestDto;
import com.test.task.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegistrationRequestDto requestDto);

    UserRegistrationDto toDto(User user);
}
