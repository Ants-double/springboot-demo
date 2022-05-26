package com.ants.security.samples.handler;


import com.ants.security.samples.pojo.LoginUser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

/**
 * @author lyy
 * @Deprecated
 * @date 2021/8/17
 */
@Slf4j
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MyUserDetailsServiceImpl myUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 这个获取表单输入中的用户名
        String userName = authentication.getName();
        String password = (String) authentication.getCredentials();
        String encodePassword = passwordEncoder.encode(password);
        LoginUser userDetails = (LoginUser) myUserDetailsService.loadUserByUsername(userName);
        if (password == null) {
            throw new UsernameNotFoundException("用户名或者密码不正确");
        }

        if (StringUtils.pathEquals(userName,userDetails.getUsername())&&
           StringUtils.pathEquals(password,userDetails.getPassword())){
            log.warn("{}登录时间为{}",userName,LocalDateTime.now().toString());
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            return new UsernamePasswordAuthenticationToken( userDetails, encodePassword, authorities);
        }
        else {
            throw new UsernameNotFoundException("用户名或者密码不正确");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
//        return (UsernamePasswordAuthenticationToken.class
//                .isAssignableFrom(authentication));
        return true;
    }
}
