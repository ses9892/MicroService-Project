package com.service.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration  //다른 Bean보다 우선순위 최고
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception{
        auth.inMemoryAuthentication()
                .withUser("admin") //id
                .password("{noop}0000") //pw
                .roles("USER"); //권한
    }

    // Security 특성상 처음에 모든 요청을 차단하기때문에 /users/** uri가 호출되는것은 허용하게 만든다.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //허용 요청 / 어떤것이매치? / 모두허용
        http.authorizeRequests().antMatchers("/users/**").permitAll();
        //h2-console 접근
        http.headers().frameOptions().disable();
    }


}
