package com.ids.app.web.security.filter;

import com.ids.app.domain.service.UserDetailsService;
import com.ids.app.web.security.JwtAuthenticationEntryPoint;
import com.ids.app.web.security.JwtUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilterRequest extends OncePerRequestFilter {
    private  Log LOGGER = LogFactory.getLog(JwtFilterRequest.class);
    private JwtUtil jwtUtil;
    private JwtAuthenticationEntryPoint entryPoint;
    private UserDetailsService userDetailService;
    @Autowired
    public JwtFilterRequest(JwtUtil jwtUtil,JwtAuthenticationEntryPoint entryPoint,UserDetailsService userDetailService){
        this.jwtUtil = jwtUtil;
        this.entryPoint = entryPoint;
        this.userDetailService = userDetailService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            LOGGER.info("Filter request...");
            String token = jwtUtil.resolveToken(request);
            if(token != null && jwtUtil.validateToken(token)){
                LOGGER.info("Filter request :: loading by user name...");
                UserDetails userDetails = userDetailService.loadUserByUsername(jwtUtil.extractUsername(token));
                LOGGER.info("Filter request :: user name "+userDetails.getUsername());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
                LOGGER.info("Filter request :: authentication "+authentication);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                LOGGER.info("Filter request :: request "+request);
            }

        } catch (AuthenticationException ex){
            LOGGER.info("filter request :: exception "+ex.getMessage());
            SecurityContextHolder.clearContext();
            entryPoint.commence(request,response,ex);
            return;
        }
        filterChain.doFilter(request,response);
    }
}
