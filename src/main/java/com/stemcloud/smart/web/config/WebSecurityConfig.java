package com.stemcloud.smart.web.config;

import com.stemcloud.smart.web.service.security.CustomUserService;
import com.stemcloud.smart.web.service.security.MySecurityFilterInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/4
 * Description: base on spring security, security相关配置
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    MySecurityFilterInterceptor mySecurityFilterInterceptor() {
        logger.info("============= Get Filter Bean ==============");
        return new MySecurityFilterInterceptor();
    }

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

    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(customUserService()).passwordEncoder(passwordEncoder());
        // 不删除凭据，以便记住用户
        builder.eraseCredentials(false);
    }

    public void configure(WebSecurity webSecurity){
        /* 访问时忽略静态相关资源 */
        webSecurity.ignoring().antMatchers("/source/**", "/js/**", "/css/**", "/img/**");

        /* 访问时忽略主页以及相关资源 */
        webSecurity.ignoring().antMatchers(/*"/", "/index*", "/denied"*/);
    }

    /**
     * 拦截器先拦，再过默认配置
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        int cookieValidSeconds = 60*60*24;
        httpSecurity.authorizeRequests()
                /*  主页以及主页相关数据无需登录权限认证  */
                .antMatchers("/", "/index/**", "/denied/**").permitAll()
                .anyRequest().authenticated()   /* 其他所有资源都需要认证，登陆后访问 */
                .and()
                /* 登陆相关设置 */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .permitAll()    /*  登陆页面用户任意访问  */
                // .successHandler(loginSuccessHandler())  /* 登陆成功之后执行 */
                .and()
                /* 退出登陆，回到主页 */
                .logout()
                .logoutSuccessUrl("/")
                /*  注销行为用户任意访问  */
                .permitAll()
                .invalidateHttpSession(true)
                .and()
                /* 开启cookie保存用户数据 */
                .rememberMe()
                /* 设置cookie有效期 */
                .tokenValiditySeconds(cookieValidSeconds);
        /* 添加自定义拦截器 */
        httpSecurity.addFilterBefore(mySecurityFilterInterceptor(), FilterSecurityInterceptor.class);
        httpSecurity.csrf().disable();
    }
}
