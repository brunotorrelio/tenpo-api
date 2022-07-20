package com.btorrelio.tenpoapi.service;

import com.btorrelio.tenpoapi.entity.TokenBlacklist;
import com.btorrelio.tenpoapi.repository.TokenBlacklistRepository;
import com.btorrelio.tenpoapi.service.impl.JwtServiceImpl;
import com.btorrelio.tenpoapi.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JwtServiceImpl.class)
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @MockBean
    private TokenBlacklistRepository tokenBlacklistRepositoryMock;

    @MockBean
    private DateTimeUtil dateTimeUtilMock;

    @Test
    void given_userDetails_when_generateTokenIsCalled_then_returnToken() {
        // Given
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("Admin", "Admin",
                new ArrayList<>());

        when(dateTimeUtilMock.getDateNow()).thenReturn(new Date(1658102400000L)); // 18/07/22 00:00:00
        when(dateTimeUtilMock.getDateNowAfterMillis(anyLong())).thenReturn(new Date(1658106000000L)); // 18/07/22 01:00:00

        // When
        String token = jwtService.generateToken(userDetails);

        // Then
        assertNotNull(token);
        assertEquals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbiIsImV4cCI6MTY1ODEwNjAwMCwiaWF0IjoxNjU4MTAyNDAwfQ.mnUFjaGvgnnRzlLffmrxVPKi_pzB_IiKfxx5VFIfPBob3_fZ-Nls34Rqk727UMMIDCvUitHBRVoHVHv2FSvyTg", token);

    }

    @Test
    void given_tokenValid_when_saveTokenBlackListIsCalled_then_saveToken() {
        // Given
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbiIsImV4cCI6MTY1ODEwNjAwMCwiaWF0IjoxNjU4MTAyNDAwfQ.mnUFjaGvgnnRzlLffmrxVPKi_pzB_IiKfxx5VFIfPBob3_fZ-Nls34Rqk727UMMIDCvUitHBRVoHVHv2FSvyTg";
        TokenBlacklist tokenBlacklist = new TokenBlacklist();
        tokenBlacklist.setToken(token);

        when(tokenBlacklistRepositoryMock.save(tokenBlacklist)).thenReturn(new TokenBlacklist());

        // When
        jwtService.saveTokenBlackList(token);

        // Then
        verify(tokenBlacklistRepositoryMock, times(1)).save(any());

    }

    @Test
    void given_requestWithAuthorizationHeader_when_getTokenIsCalled_then_returnTokenPresent() {
        // Given
        String requestToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbiIsImV4cCI6MTY1ODEwNjAwMCwiaWF0IjoxNjU4MTAyNDAwfQ.mnUFjaGvgnnRzlLffmrxVPKi_pzB_IiKfxx5VFIfPBob3_fZ-Nls34Rqk727UMMIDCvUitHBRVoHVHv2FSvyTg";
        String authorizationHeader = "Bearer " + requestToken;
        MockHttpServletRequest requestMock = new MockHttpServletRequest();
        requestMock.addHeader("Authorization", authorizationHeader);

        // When
        Optional<String> token = jwtService.getToken(requestMock);

        // Then
        assertTrue(token.isPresent());
        assertEquals(requestToken, token.get());

    }

    @Test
    void given_requestWithAuthorizationHeaderWithoutBearer_when_getTokenIsCalled_then_returnTokenEmpty() {
        // Given
        String requestToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbiIsImV4cCI6MTY1ODEwNjAwMCwiaWF0IjoxNjU4MTAyNDAwfQ.mnUFjaGvgnnRzlLffmrxVPKi_pzB_IiKfxx5VFIfPBob3_fZ-Nls34Rqk727UMMIDCvUitHBRVoHVHv2FSvyTg";
        String authorizationHeader = requestToken;
        MockHttpServletRequest requestMock = new MockHttpServletRequest();
        requestMock.addHeader("Authorization", authorizationHeader);

        // When
        Optional<String> token = jwtService.getToken(requestMock);

        // Then
        assertTrue(token.isEmpty());

    }



}
