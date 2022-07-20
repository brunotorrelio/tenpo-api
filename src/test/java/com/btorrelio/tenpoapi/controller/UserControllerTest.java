package com.btorrelio.tenpoapi.controller;

import com.btorrelio.tenpoapi.dto.ResponseLoginDto;
import com.btorrelio.tenpoapi.dto.UserDto;
import com.btorrelio.tenpoapi.dto.UserLoginDto;
import com.btorrelio.tenpoapi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest extends AbstractControllerTest{

    private final String BASE_URL_API = "/user";

    @MockBean
    private UserServiceImpl userServiceMock;

    @Test
    void given_UsernameAndPassword_when_signUpIsCalled_then_returnSuccess() throws Exception{
        UserLoginDto userLoginDto = UserLoginDto.builder()
                .username("Admin")
                .password("Admin")
                .build();

        String userLoginJson = super.mapToJson(userLoginDto);

        UserDto userDto = UserDto.builder()
                .id(1L)
                .username("Admin")
                .build();

        when(userServiceMock.register(any(), any())).thenReturn(userDto);

        mockMvc.perform(post(BASE_URL_API + "/signup")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(userLoginJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("Admin"));
    }

    @Test
    void given_UsernameButNotPassword_when_signUpIsCalled_then_returnBadRequest() throws Exception{
        UserLoginDto userLoginDto = UserLoginDto.builder()
                .username("Admin")
                .build();

        String userLoginJson = super.mapToJson(userLoginDto);

        mockMvc.perform(post(BASE_URL_API + "/signup")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(userLoginJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void given_passwordButNotUsername_when_signUpIsCalled_then_returnBadRequest() throws Exception{
        UserLoginDto userLoginDto = UserLoginDto.builder()
                .password("Admin")
                .build();

        String userLoginJson = super.mapToJson(userLoginDto);

        mockMvc.perform(post(BASE_URL_API + "/signup")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(userLoginJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void given_usernameExistAndPassword_when_signUpIsCalled_then_returnConflict() throws Exception{
        UserLoginDto userLoginDto = UserLoginDto.builder()
                .username("Admin")
                .password("Admin")
                .build();

        String userLoginJson = super.mapToJson(userLoginDto);

        UserDto userDto = UserDto.builder()
                .id(1L)
                .username("Admin")
                .build();

        when(userServiceMock.register(any(), any())).thenThrow(new IllegalStateException("The user is already registered"));

        mockMvc.perform(post(BASE_URL_API + "/signup")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(userLoginJson))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void given_usernameAndPassword_when_loginIsCalled_then_returnSuccess() throws Exception{
        UserLoginDto userLoginDto = UserLoginDto.builder()
                .username("Admin")
                .password("Admin")
                .build();

        String userLoginJson = super.mapToJson(userLoginDto);

        ResponseLoginDto responseLoginDto = ResponseLoginDto.builder()
                .token("123456789abcdefghijklmnopqrstuvwxyz")
                .timeStamp(LocalDateTime.now())
                .build();

        when(userServiceMock.login(any(), any())).thenReturn(responseLoginDto);

        mockMvc.perform(post(BASE_URL_API + "/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(userLoginJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("123456789abcdefghijklmnopqrstuvwxyz"));
    }

    @Test
    void given_usernameButNotPassword_when_loginIsCalled_then_returnBadRequest() throws Exception{
        UserLoginDto userLoginDto = UserLoginDto.builder()
                .username("Admin")
                .build();

        String userLoginJson = super.mapToJson(userLoginDto);

        mockMvc.perform(post(BASE_URL_API + "/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(userLoginJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void given_passwordButNotUsername_when_loginIsCalled_then_returnBadRequest() throws Exception{
        UserLoginDto userLoginDto = UserLoginDto.builder()
                .password("Admin")
                .build();

        String userLoginJson = super.mapToJson(userLoginDto);

        mockMvc.perform(post(BASE_URL_API + "/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(userLoginJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    void given_request_when_signOffIsCalled_then_returnSuccess() throws Exception{
        doNothing().when(userServiceMock).signOff(any());

        mockMvc.perform(post(BASE_URL_API + "/signoff"))
                .andExpect(status().isOk());
    }

    @Test
    void given_requestWithoutAuthentication_when_signOffIsCalled_then_returnUUnauthorized() throws Exception{
        mockMvc.perform(post(BASE_URL_API + "/signoff"))
                .andExpect(status().isUnauthorized());
    }

}
