package com.btorrelio.tenpoapi.service.impl;

import com.btorrelio.tenpoapi.entity.TokenBlacklist;
import com.btorrelio.tenpoapi.repository.TokenBlacklistRepository;
import com.btorrelio.tenpoapi.service.JwtService;
import com.btorrelio.tenpoapi.util.DateTimeUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    public static final long JWT_TOKEN_VALIDITY = 60 * 60; // 1 hour

    private final TokenBlacklistRepository tokenBlacklistRepository;

    private final DateTimeUtil dateTimeUtil;

    public JwtServiceImpl(TokenBlacklistRepository tokenBlacklistRepository, DateTimeUtil dateTimeUtil) {
        this.tokenBlacklistRepository = tokenBlacklistRepository;
        this.dateTimeUtil = dateTimeUtil;
    }

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(dateTimeUtil.getDateNow());
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(dateTimeUtil.getDateNow())
                .setExpiration(dateTimeUtil.getDateNowAfterMillis(JWT_TOKEN_VALIDITY*1000)).signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);


        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !isTokenBlacklisted(token));
    }

    @Override
    @Transactional
    public void saveTokenBlackList(String token) {
        log.info("Adding token {} to blacklist", token);
        TokenBlacklist tokenBlacklist = new TokenBlacklist();
        tokenBlacklist.setToken(token);
        tokenBlacklistRepository.save(tokenBlacklist);
    }

    @Override
    public Optional<String> getToken(HttpServletRequest request) {
        Optional<String> token = Optional.empty();
        String authorization = request.getHeader("Authorization");
        if(authorization != null  && authorization.startsWith("Bearer ")) {
            token = Optional.of(authorization.substring(7));
        }
        return token;
    }

    private Boolean isTokenBlacklisted(String token) {
        return tokenBlacklistRepository.existsByToken(token);
    }

}
