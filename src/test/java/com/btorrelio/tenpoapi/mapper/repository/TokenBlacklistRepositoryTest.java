package com.btorrelio.tenpoapi.mapper.repository;

import com.btorrelio.tenpoapi.repository.TokenBlacklistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TokenBlacklistRepositoryTest {

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    @Test
    void given_existingToken_when_existsByTokenIsCalled_then_returnTrue() {
        // Given
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHJpbmciLCJleHAiOjE2NTgxNzc1ODEsImlhdCI6MTY1ODE1OTU4MX0.seaa7trTd7XL-TyycyowEuHRGVIj-KMN86B3r-OPYszDVLz95LgC3vF9Tk9mFw-gVbkZ5laujxRj0zycia4RZQ";

        // When
        Boolean tokenExists = tokenBlacklistRepository.existsByToken(token);

        // Then
        assertTrue(tokenExists);
    }

    @Test
    void given_nonexistentToken_when_existsByTokenIsCalled_then_returnFase() {
        // Given
        String token = "czKhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHJpbmciLCJasd213dsgDAD1ODEsImlhdCI6MTY1ODE1OTU4MX0.seaa7trTd7XL-TyycyowEuHRGVIj-KMN86B3r-OPYszDVLz95LgC3vF9Tk9mFw-gVbkZ5laujxRj0zycia4RZQ";

        // When
        Boolean tokenExists = tokenBlacklistRepository.existsByToken(token);

        // Then
        assertFalse(tokenExists);
    }

}
