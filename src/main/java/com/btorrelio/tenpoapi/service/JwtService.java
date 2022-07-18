package com.btorrelio.tenpoapi.service;

import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface JwtService {

    String getUsernameFromToken(String token);

    String generateToken(UserDetails userDetails);

    Boolean validateToken(String token, UserDetails userDetails);

    void saveTokenBlackList(String token);

    Optional<String> getToken(HttpServletRequest request);

}
