package com.stemcloud.smart.web.config;

import com.stemcloud.smart.web.service.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/4
 * Description: base on spring security
 */
// @Configuration
// @EnableWebSecurity
public class WebSecurityConfig /*extends WebSecurityConfiguration*/{

    /*@Bean
    UserDetailsService customUserService(){
        return new CustomUserService();
    }

    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(customUserService());
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .anyRequest().authenticated().and().formLogin().loginPage("/login")
                .failureUrl("/login?error")
                .permitAll().and().logout().permitAll();
    }*/
}
