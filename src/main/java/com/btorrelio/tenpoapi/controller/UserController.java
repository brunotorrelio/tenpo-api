package com.btorrelio.tenpoapi.controller;

import com.btorrelio.tenpoapi.dto.ResponseLoginDto;
import com.btorrelio.tenpoapi.dto.UserDto;
import com.btorrelio.tenpoapi.dto.UserLoginDto;
import com.btorrelio.tenpoapi.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@Tag(name = "Users")
@RestController
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody UserLoginDto userDto) {
        return ResponseEntity.ok(userService.register(userDto.getUsername(), userDto.getPassword()));
    }

    @PostMapping("login")
    public ResponseEntity<ResponseLoginDto> login(@Valid @RequestBody UserLoginDto userLogin) {
        log.info("login");
        return ResponseEntity.ok(userService.login(userLogin.getUsername(), userLogin.getPassword()));
    }

    @PostMapping("signoff")
    public ResponseEntity<Void> signoff(HttpServletRequest request) {
        userService.signOff(request);
        return ResponseEntity.ok().build();
    }
}
