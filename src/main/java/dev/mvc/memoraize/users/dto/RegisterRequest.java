package dev.mvc.memoraize.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;


/**
 * 회원가입 요청 DTO
 * - 클라이언트(Next.js)에서 오는 JSON을 이 타입으로 받습니다.
 * - @Valid로 검증할 수 있게 Bean Validation 어노테이션을 붙여둡니다.
 */
public record RegisterRequest(

        // 이름: 필수, 최대 50자 (엔티티 @Column(length=50)와 맞춤)
        @NotBlank(message = "이름은 필수입니다.")
        @Size(max = 50, message = "이름은 50자 이하여야 합니다.")
        String name,

        // 이메일: 필수, 이메일 형식, 최대 100자
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @Size(max = 100, message = "이메일은 100자 이하여야 합니다.")
        String email,

        // 비밀번호: 필수, 최소 8자 ~ 최대 72자 (BCrypt는 72자까지만 반영)
        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 72, message = "비밀번호는 8~72자여야 합니다.")
        String password,

        // 닉네임: 필수, 최대 50자
        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(max = 50, message = "닉네임은 50자 이하여야 합니다.")
        String username,

        // 프롬프트: 선택값. 길이 제한은(원한다면) 나중에 정책에 맞춰 추가하세요.
        String prompt
) {}

