package com.stemcloud.smart.web.config;

import com.stemcloud.smart.web.service.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/4
 * Description: base on spring security, security相关配置
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService customUserService(){
        return new CustomUserService();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }

    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(customUserService());
        /*.passwordEncoder(passwordEncoder());
        builder.eraseCredentials(false);*/
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/","/app/**").permitAll()   /* 访问这些页面无需认证权限 */
                .antMatchers("/map/sensor").permitAll()
                .antMatchers("/source/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/img/**").permitAll()
                // .antMatchers("/login").permitAll()
                .anyRequest().authenticated()   /* 其他所有资源都需要认证，登陆后访问 */
                .antMatchers("/class").hasAuthority("ADMIN") /* 登陆后只有ADMIN角色可以访问class页面 */
                .and()
                /* 登陆相关设置 */
                .formLogin()
                .loginPage("/login")
                // .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .permitAll()
                .successHandler(loginSuccessHandler())  /* 成功之后跳转 */
                .and()
                /* 退出登陆，回到主页 */
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
                .invalidateHttpSession(true)
                .and()
                /* 开启cookie保存用户数据 */
                .rememberMe()
                .tokenValiditySeconds(60 * 60 * 24 * 7);    /* 设置cookie有效期 */
    }
}
