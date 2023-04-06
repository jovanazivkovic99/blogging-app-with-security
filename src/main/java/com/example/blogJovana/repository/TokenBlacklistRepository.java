package com.example.blogJovana.repository;

import com.example.blogJovana.model.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
    Boolean existsByToken(String token);
}
