package dev.mvc.memoraize.users.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import dev.mvc.memoraize.userwords.domain.UserWord;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이름
    @Column(length = 50, nullable = false, unique = true)
    private String name;

    // 아이디
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    // 비밀번호
    @Column(length = 255, nullable = false)
    private String password;
    
    //닉네임
    @Column(length = 50, nullable = false, unique = true)
    private String username;

    // 프롬프트
    @Column(columnDefinition = "TEXT")
    private String prompt;

    //가입일 (수정될 때 값 수정 X)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 정보 수정일
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // user_words테이블과 양방향
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserWord> userWords = new ArrayList<>();


}



