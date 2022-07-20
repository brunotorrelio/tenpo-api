package com.btorrelio.tenpoapi.mapper;

import com.btorrelio.tenpoapi.dto.UserDto;
import com.btorrelio.tenpoapi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UserMapperImpl.class)
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void given_userEntity_when_entity2DtoIsCalled_then_returnUserDto() {
        // Given
        User userEntity = User.builder()
                .id(1L)
                .username("Admin")
                .password("Password")
                .build();

        // When
        UserDto userDto = userMapper.entity2Dto(userEntity);

        // Then
        assertNotNull(userDto);
        assertEquals(1L, userDto.getId());
        assertEquals("Admin", userDto.getUsername());
        assertEquals("Password", userDto.getPassword());

    }

    @Test
    void given_userDto_when_dto2EntityIsCalled_then_returnUserEntity() {
        // Given
        UserDto userDto = UserDto.builder()
                .id(1L)
                .username("Admin")
                .password("Password")
                .build();

        // When
        User userEntity = userMapper.dto2Entity(userDto);

        // Then
        assertNotNull(userEntity);
        assertEquals(1L, userEntity.getId());
        assertEquals("Admin", userEntity.getUsername());
        assertEquals("Password", userEntity.getPassword());

    }

}
