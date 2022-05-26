package com.ants.security.samples.handler;


import com.ants.security.samples.pojo.LoginUser;
import com.ants.security.samples.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lyy
 * @Deprecated
 * @date 2021/8/17
 */
@Slf4j
@Component
public class MyUserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 根据名子（标量）从数据库中查询的真实信息
        User build =null;
        List<String> list=null;
        switch (username){
            case "ants":
                build = User.builder().userName("ants").password("double").build();
                list = Arrays.asList("ADMIN","MANGER");
                break;
            case "ants1":
                build = User.builder().userName("ants1").password("double").build();
                list = Arrays.asList("UNIVERSAL");
                break;
        }



        return LoginUser.builder().user(build).roles(list).build();
    }
}
