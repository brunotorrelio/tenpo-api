package com.btorrelio.tenpoapi.controller;

import com.btorrelio.tenpoapi.dto.ResponseLoginDto;
import com.btorrelio.tenpoapi.dto.UserDto;
import com.btorrelio.tenpoapi.dto.UserLoginDto;
import com.btorrelio.tenpoapi.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "Users")
@RestController
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("signup")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody UserLoginDto userDto) {
        return ResponseEntity.ok(userService.register(userDto.getUsername(), userDto.getPassword()));
    }

    @PostMapping("login")
    public ResponseEntity<ResponseLoginDto> login(@Valid @RequestBody UserLoginDto userLogin) {
        return ResponseEntity.ok(userService.login(userLogin.getUsername(), userLogin.getPassword()));
    }

    @PostMapping("signoff")
    public ResponseEntity<Void> signOff(HttpServletRequest request) {
        userService.signOff(request);
        return ResponseEntity.ok().build();
    }
}
