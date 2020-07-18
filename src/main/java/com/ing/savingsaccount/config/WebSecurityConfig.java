package com.ing.savingsaccount.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails user1 = User
                .withUsername("cosmin1")
                .password("$2a$10$2CZOGA7ltX5KVip0LGwpCez4YQKsp48jKO4VwwE5cIP9KsGPJvK6O")//cosmin
                .roles("USER")
                .build();
        UserDetails user2 = User
                .withUsername("cosmin2")
                .password("$2a$10$2CZOGA7ltX5KVip0LGwpCez4YQKsp48jKO4VwwE5cIP9KsGPJvK6O")//cosmin
                .roles("USER")
                .build();
        UserDetails user3 = User
                .withUsername("cosmin3")
                .password("$2a$10$2CZOGA7ltX5KVip0LGwpCez4YQKsp48jKO4VwwE5cIP9KsGPJvK6O")//cosmin
                .roles("USER")
                .build();
        UserDetails user4 = User
                .withUsername("admin")
                .password("$2a$10$GYEeIGX41CwnhTCQjAJeze0JbpQLtBhIg/O78bCKsQ7Hvs0njMmdq")//admin
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2, user3, user4);
    }

    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/", "/home", "/console/**").permitAll()
                .mvcMatchers("/cpanel").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username").passwordParameter("pass")
                .permitAll()
                .failureUrl("/loginerror")
                .defaultSuccessUrl("/loginsuccess")
                .and()
                .logout().permitAll()
                .logoutSuccessUrl("/logoutsuccess")
                .and()
                .exceptionHandling().accessDeniedPage("/403");

        httpSecurity.csrf().disable();//to enable h2 console alongside spring security
        httpSecurity.headers().frameOptions().disable();//to enable h2 console alongside spring security
    }
}
