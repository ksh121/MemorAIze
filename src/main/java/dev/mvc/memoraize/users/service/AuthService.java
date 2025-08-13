package dev.mvc.memoraize.users.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.mvc.memoraize.users.domain.User;
import dev.mvc.memoraize.users.dto.RegisterRequest;
import dev.mvc.memoraize.users.dto.RegisterResponse;
import dev.mvc.memoraize.users.domain.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * 인증/회원가입 관련 비즈니스 로직.
 * - 컨트롤러에서 DTO를 받아 실제 저장/검증/암호화 처리
 * - JPA 리포지토리와 PasswordEncoder 사용
 */
@Service
@RequiredArgsConstructor // 👉 생성자 자동 생성 (필드 3개 주입). record가 아닌 '클래스'라서 Lombok을 씀.
public class AuthService {

    private final UserRepository userRepository; // DB 접근
    private final PasswordEncoder passwordEncoder; // 비밀번호 해시(BCrypt)

    /**
     * 회원가입 처리
     * 1) 중복 체크
     * 2) 비밀번호 해시
     * 3) 엔티티 생성/저장
     * 4) 응답 DTO로 변환
     */
    @Transactional
    public RegisterResponse register(RegisterRequest req) {

        // 1) 중복 체크 — existsBy... 는 "존재 여부만" true/false 로 빠르게 확인
        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        if (userRepository.existsByUsername(req.username())) {
            throw new IllegalArgumentException("이미 사용 중인 사용자명입니다.");
        }
        if (userRepository.existsByName(req.name())) {
            throw new IllegalArgumentException("이미 사용 중인 이름입니다.");
        }

        try {
            // 2) 비밀번호 해시 — 평문 비밀번호를 DB에 절대 저장하지 않음
            String encodedPw = passwordEncoder.encode(req.password());

            // 3) 엔티티 생성 — User는 @Builder가 달려 있으니 빌더로 생성
            //    (여기서 record의 역할: req.name()/req.email() 같은 '자동 getter'를 제공)
            User saved = userRepository.save(
                User.builder()
                    .name(req.name())
                    .email(req.email())
                    .username(req.username())
                    .password(encodedPw)   // 해시된 값 저장!
                    .prompt(req.prompt())
                    .build()
            );

            // 4) 응답 DTO — 비밀번호 등 민감정보는 제외하고 필요한 것만
            //    RegisterResponse는 record + @Builder 조합 → 가독성 좋게 조립
            return RegisterResponse.builder()
                    .id(saved.getId())
                    .email(saved.getEmail())
                    .username(saved.getUsername())
                    .name(saved.getName())
                    .build();

        } catch (DataIntegrityViolationException e) {
            // 동시 요청 등으로 UNIQUE 제약 위반이 발생할 수 있어 마지막 방어막
            throw new IllegalArgumentException("이미 사용 중인 정보(이메일/이름/사용자명)가 있습니다.");
        }
    }
}
