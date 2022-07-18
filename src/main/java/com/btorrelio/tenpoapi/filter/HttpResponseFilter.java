package com.btorrelio.tenpoapi.filter;

import com.btorrelio.tenpoapi.service.EndpointHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class HttpResponseFilter extends OncePerRequestFilter {

    @Autowired
    private EndpointHistoryService endpointHistoryService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !(
                request.getRequestURI().startsWith("/api/user") ||
                request.getRequestURI().startsWith("/api/calculator")
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);
        log.info("request uri: " +request.getRequestURI());
        if (response.getStatus() == 200) {
            saveHistory(request, responseWrapper);
        }

        responseWrapper.copyBodyToResponse();
    }

    private void saveHistory(HttpServletRequest request, ContentCachingResponseWrapper responseWrapper) {

        String responseBody = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
        String method = request.getMethod();
        String uri = request.getRequestURI();

        endpointHistoryService.save(uri, method, responseBody);

    }
}
