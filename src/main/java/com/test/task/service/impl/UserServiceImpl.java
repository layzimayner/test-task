package com.test.task.service.impl;

import com.test.task.dto.user.UserRegistrationDto;
import com.test.task.dto.user.UserRegistrationRequestDto;
import com.test.task.exception.RegistrationException;
import com.test.task.mapper.UserMapper;
import com.test.task.model.Role;
import com.test.task.model.User;
import com.test.task.repository.RoleRepository;
import com.test.task.repository.UserRepository;
import com.test.task.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserRegistrationDto save(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("Email is already in use");
        }
        User user = userMapper.toModel(requestDto);

        user.setRoles(Set.of(roleRepository.findByName(Role.RoleName.USER)));

        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        userRepository.save(user);

        return userMapper.toDto(user);
    }

}
