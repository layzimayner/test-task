package com.test.task.dto.user;

public record UserLoginResponseDto(String token,
                                   Long id,
                                   String firstName,
                                   String lastName,
                                   String email) {
}
