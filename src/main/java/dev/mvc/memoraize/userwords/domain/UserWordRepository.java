package dev.mvc.memoraize.userwords.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWordRepository extends JpaRepository<UserWord, Long>{
    
}
 