package com.wickedwitch.config;

import com.wickedwitch.backend.services.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ZuZ on 2017-01-06.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    /** The enctyption SALT. */
    private static final String SALT= "ksdj9843ty;98wedm9;0q3w8e40!!129edi09jow";

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private Environment environment;

    //Array of public URLs
    private static final String[] PUBLIC_MATCHERS = {
            "/webjars/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/",
            "/about/**",
            "/contact/**",
            "/error/**/*",
            "/console/**",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        activeProfile(http);

        http
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/payload")
                .failureUrl("/login?error").permitAll()
                .and()
                .logout().permitAll();
    }


    private void activeProfile(HttpSecurity http) throws Exception {
        List<String> activeProfile = Arrays.asList(environment.getActiveProfiles());
        if(activeProfile.contains("dv")){
            http.csrf().disable();
            http.headers().frameOptions().disable();
        }
    }



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception{
        managerBuilder
                .userDetailsService(userSecurityService)
                .passwordEncoder(passwordEncoder());


        // IN MEMORY AUTHENTICATION
//                .inMemoryAuthentication()
//                .withUser("user")
//                .password("password")
//                .roles("USER");

    }
}
