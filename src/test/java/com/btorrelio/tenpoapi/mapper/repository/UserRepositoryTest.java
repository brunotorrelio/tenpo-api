package com.btorrelio.tenpoapi.mapper.repository;

import com.btorrelio.tenpoapi.entity.User;
import com.btorrelio.tenpoapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void given_existingUser_when_findByUsernameIsCalled_then_returnOptionalUser() {
        // Given
        String username = "Admin";

        // When
        Optional<User> user = userRepository.findByUsername(username);

        // Then
        assertTrue(user.isPresent());
        assertEquals(1L, user.get().getId());
        assertEquals("Admin", user.get().getUsername());
        assertEquals("$2a$10$KOyJ8ZJTpvJQfM5Gz6oFxe2apUU3R544GzPDoSWNXJDWBSeNkRctO", user.get().getPassword());
    }

    @Test
    void given_nonexistentUser_when_findByUsernameIsCalled_then_returnOptionalUser() {
        // Given
        String username = "brunotorrelio";

        // When
        Optional<User> user = userRepository.findByUsername(username);

        // Then
        assertTrue(user.isEmpty());
    }

    @Test
    void given_existingUser_when_existsByUsernameIsCalled_then_returnTrue() {
        // Given
        String username = "Admin";

        // When
        Boolean userExists = userRepository.existsByUsername(username);

        // Then
        assertTrue(userExists);
    }

    @Test
    void given_nonexistentUser_when_existsByUsernameIsCalled_then_returnFalse() {
        // Given
        String username = "brunotorrelio";

        // When
        Boolean userExists = userRepository.existsByUsername(username);

        // Then
        assertFalse(userExists);
    }
}
