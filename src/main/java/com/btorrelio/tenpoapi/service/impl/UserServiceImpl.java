package com.btorrelio.tenpoapi.service.impl;

import com.btorrelio.tenpoapi.dto.ResponseLoginDto;
import com.btorrelio.tenpoapi.dto.UserDto;
import com.btorrelio.tenpoapi.entity.User;
import com.btorrelio.tenpoapi.exception.UnauthorizedException;
import com.btorrelio.tenpoapi.mapper.UserMapper;
import com.btorrelio.tenpoapi.repository.UserRepository;
import com.btorrelio.tenpoapi.service.JwtService;
import com.btorrelio.tenpoapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, UserMapper userMapper,
                           @Lazy AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    @Override
    @Transactional
    public UserDto register(String username, String password) {
        log.info("Registering user with username: {}", username);
        if (userRepository.existsByUsername(username)) {
            throw new IllegalStateException("The user is already registered");
        }

        UserDto dto = UserDto.builder()
                .username(username)
                .password(password)
                .build();

        return save(dto);
    }

    @Override
    public ResponseLoginDto login(String username, String password){
        log.info("Logging in user with username: {}", username);
        authenticate(username, password);

        UserDetails userDetails = loadUserByUsername(username);
        String token = jwtService.generateToken(userDetails);

        return ResponseLoginDto.builder()
                .token(token)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    private void authenticate(String username, String password){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Invalid credentials", e);
        }
    }

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        log.info("Saving user with username: {}", userDto.getUsername());
        User user = userMapper.dto2Entity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User newUser = userRepository.save(user);
        return userMapper.entity2Dto(newUser);
    }

    @Override
    public void signOff(HttpServletRequest request) {
        Optional<String> token = jwtService.getToken(request);

        if (token.isEmpty()) {
            throw new UnauthorizedException("Token not present");
        }
        jwtService.saveTokenBlackList(token.get());

    }

}
