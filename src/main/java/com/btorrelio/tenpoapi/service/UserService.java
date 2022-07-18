package com.btorrelio.tenpoapi.service;

import com.btorrelio.tenpoapi.dto.ResponseLoginDto;
import com.btorrelio.tenpoapi.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    UserDto register(String username, String password);
    ResponseLoginDto login(String username, String password);
    UserDto save(UserDto user);
    void signOff(HttpServletRequest request);
}
