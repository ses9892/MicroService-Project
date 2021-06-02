package com.service.userservice.config;

import com.service.userservice.security.AuthenticationFilter;
import com.service.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;

@Configuration  //다른 Bean보다 우선순위 최고
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private Environment environment;

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
//        http.authorizeRequests().antMatchers("/users/**").permitAll();
        //모든 요청을 security가 허용하는것이아닌 172.30.1.17의 IP를 허용하고
        //getAuthentionFilter를 통과시킨 데이터를 통해서만 권환,데이터 부여한다.
        http.authorizeRequests().antMatchers("/**")
                .hasIpAddress("127.0.0.1").and().addFilter(getAuthentionFilter());

        //h2-console 접근
        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthentionFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService,environment,authenticationManager()); //인증처리
        authenticationFilter.setAuthenticationManager(authenticationManager()); //인증처리하기위해
        //WebSecurityConfigurerAdapter에서 가져온 authenticationManager()로 인증처리를한다.
        return  authenticationFilter;
    }

    //인수를 Http를 쓰고있으면 : 권한
    //인수를 AuthenticationManagerBuilder를 쓰고 있으면 : 인증
    @Override                   //인증
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

}










