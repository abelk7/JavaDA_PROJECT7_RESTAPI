package com.nnk.springboot.config;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class SecurityConfig {
    @Autowired
    private IUserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] staticResources = {
                "/css/**",
                "/images/**",
                "/fonts/**",
                "/scripts/**",
        };

        http
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/", "/home").permitAll()
                        .antMatchers("/user/list/**", "/user/add/**", "/user/validate/**" , "/user/update/**", "/user/delete/**", "/user/add").permitAll()
                        .antMatchers(staticResources).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/bidList/list")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        http.csrf().disable();
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userService.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        };
    }
}
