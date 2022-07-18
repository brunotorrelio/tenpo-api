package com.btorrelio.tenpoapi.repository;

import com.btorrelio.tenpoapi.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {

    Boolean existsByToken(String token);

}
