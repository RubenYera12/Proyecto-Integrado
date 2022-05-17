package com.ruben.rfaf.shared.security.config;

import com.ruben.rfaf.shared.security.filter.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //TODO: Borrar el metodo, es solo para el desarrollo
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers()
                .permitAll()
                .antMatchers("/login")
                .permitAll()
                .antMatchers("/api/match/assing/**")
                .hasAnyRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "/student/addStudent")
//                .hasAnyRole("ADMIN")
                .anyRequest()
                .authenticated();
    }
}
