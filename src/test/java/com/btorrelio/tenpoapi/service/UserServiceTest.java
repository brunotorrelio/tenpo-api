package com.btorrelio.tenpoapi.service;

import com.btorrelio.tenpoapi.dto.ResponseLoginDto;
import com.btorrelio.tenpoapi.dto.UserDto;
import com.btorrelio.tenpoapi.entity.User;
import com.btorrelio.tenpoapi.exception.UnauthorizedException;
import com.btorrelio.tenpoapi.mapper.UserMapper;
import com.btorrelio.tenpoapi.repository.UserRepository;
import com.btorrelio.tenpoapi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @SpyBean
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepositoryMock;

    @MockBean
    private PasswordEncoder passwordEncoderMock;

    @MockBean
    private UserMapper userMapperMock;

    @MockBean
    private AuthenticationManager authenticationManagerMock;

    @MockBean
    private JwtService jwtServiceMock;

    @Test
    void given_username_when_loadUserByUsernameIsCalled_then_returnUserDetails() {
        // Given
        String username = "Admin";

        User userEntity = User.builder()
                .id(1L)
                .username(username)
                .password("encodedPassword")
                .build();

        when(userRepositoryMock.findByUsername(any())).thenReturn(Optional.of(userEntity));

        // When
        UserDetails userDetails = userService.loadUserByUsername(username);

        // Then
        assertNotNull(userDetails);
        assertEquals("Admin", userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());

    }

    @Test
    void given_nonexistUsername_when_loadUserByUsernameIsCalled_then_returnUsernameNotFoundException() {
        // Given
        String username = "Admin";
        when(userRepositoryMock.findByUsername(any())).thenReturn(Optional.empty());

        // When - Then
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));

    }

    @Test
    void given_usernameAndPassword_when_registerIsCalling_then_returnUserDto() {
        // Given
        String username = "Admin";
        String password = "Admin";

        UserDto finalDto = UserDto.builder()
                .id(1L)
                .username(username)
                .password(password)
                .build();

        when(userRepositoryMock.existsByUsername(any())).thenReturn(false);
        doReturn(finalDto).when(userService).save(any());

        // When
        UserDto userDto = userService.register(username, password);

        // Then
        assertNotNull(userDto);
        assertEquals(1L, userDto.getId());
        assertEquals(username, userDto.getUsername());
        assertEquals(password, userDto.getPassword());

    }

    @Test
    void given_existingUsernameAndPassword_when_registerIsCalling_thenReturnIllegalStateException() {
        // Given
        String username = "Admin";
        String password = "Admin";

        when(userRepositoryMock.existsByUsername(username)).thenReturn(true);

        // When - Then
        assertThrows(IllegalStateException.class, () -> userService.register(username, password));

    }

    @Test
    void given_userDto_when_saveIsCalling_then_returnNewUserDto() {
        // Given
        UserDto initialUserDto = UserDto.builder()
                .username("Admin")
                .password("Password")
                .build();

        User initialUserEntity = User.builder()
                .username("Admin")
                .password("Password")
                .build();

        User finalUserEntity = User.builder()
                .id(1L)
                .username("Admin")
                .password("encriptedPassword")
                .build();

        UserDto finalUserDto = UserDto.builder()
                .id(1L)
                .username("Admin")
                .password("encriptedPassword")
                .build();

        when(userMapperMock.dto2Entity(any())).thenReturn(initialUserEntity);
        when(passwordEncoderMock.encode(any())).thenReturn("encriptedPassword");
        when(userMapperMock.entity2Dto(any())).thenReturn(finalUserDto);

        // When
        UserDto userDto = userService.save(initialUserDto);

        // Then
        assertNotNull(userDto);
        assertEquals(1L, userDto.getId());
        assertEquals("Admin", userDto.getUsername());
        assertEquals("encriptedPassword", userDto.getPassword());

    }

    @Test
    void given_usernameAndPassword_when_loginIsCalled_then_ReturnResponseLoginDto() {
        // Given
        String username = "Admin";
        String password = "Admin";

        UserDetails userDetails = new org.springframework.security.core.userdetails.User("Admin", "Admin",
                new ArrayList<>());

        when(authenticationManagerMock.authenticate(any())).thenReturn(null);
        doReturn(userDetails).when(userService).loadUserByUsername(any());
        when(jwtServiceMock.generateToken(any())).thenReturn("token");

        // When
        ResponseLoginDto responseLoginDto = userService.login(username, password);

        // Then
        assertNotNull(responseLoginDto);
        assertEquals("token", responseLoginDto.getToken());

    }

    @Test
    void given_nonexistentUsernameAndPassword_when_loginIsCalled_then_ReturnUsernameNotFoundException() {
        // Given
        String username = "Admin";
        String password = "Admin";

        when(authenticationManagerMock.authenticate(any())).thenReturn(null);
        when(userRepositoryMock.findByUsername(any())).thenReturn(Optional.empty());

        // When - Then
        assertThrows(UsernameNotFoundException.class, () -> userService.login(username, password));
    }

    @Test
    void given_requestWithAuthenticationHeader_when_signOffIsCalled_then_addTokenInBlacklist() {
        // Given
        String requestToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbiIsImV4cCI6MTY1ODEwNjAwMCwiaWF0IjoxNjU4MTAyNDAwfQ.mnUFjaGvgnnRzlLffmrxVPKi_pzB_IiKfxx5VFIfPBob3_fZ-Nls34Rqk727UMMIDCvUitHBRVoHVHv2FSvyTg";
        String authorizationHeader = "Bearer" + requestToken;;
        MockHttpServletRequest requestMock = new MockHttpServletRequest();
        requestMock.addHeader("Authorization", authorizationHeader);

        when(jwtServiceMock.getToken(any())).thenReturn(Optional.of(requestToken));
        doNothing().when(jwtServiceMock).saveTokenBlackList(any());

        // When
        userService.signOff(requestMock);

        // Then
        verify(jwtServiceMock, times(1)).saveTokenBlackList(requestToken);

    }

    @Test
    void given_requestWithoutAuthenticationHeader_when_signOffIsCalled_then_returnUnauthorizedException() {
        // Given
        String requestToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbiIsImV4cCI6MTY1ODEwNjAwMCwiaWF0IjoxNjU4MTAyNDAwfQ.mnUFjaGvgnnRzlLffmrxVPKi_pzB_IiKfxx5VFIfPBob3_fZ-Nls34Rqk727UMMIDCvUitHBRVoHVHv2FSvyTg";
        String authorizationHeader = requestToken;
        MockHttpServletRequest requestMock = new MockHttpServletRequest();
        requestMock.addHeader("Authorization", authorizationHeader);

        when(jwtServiceMock.getToken(any())).thenReturn(Optional.empty());

        // When - Then
        assertThrows(UnauthorizedException.class, () -> userService.signOff(requestMock));

    }

}
