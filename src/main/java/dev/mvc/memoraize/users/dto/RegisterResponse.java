package dev.mvc.memoraize.users.dto;

import lombok.Builder;

/** 회원가입 응답 DTO */
@Builder
public record RegisterResponse(
        Long id,
        String email,
        String username,
        String name
) {}
