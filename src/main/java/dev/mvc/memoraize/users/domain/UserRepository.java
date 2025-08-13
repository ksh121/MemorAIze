package dev.mvc.memoraize.users.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByName(String name);
    Optional<User> findByEmail(String email);
}
