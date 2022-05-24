package com.gmail.burinigor7.apigatewayservice.sercurity.jwt;

import com.gmail.burinigor7.apigatewayservice.filter.JwtTokenFilter;
import com.gmail.burinigor7.apigatewayservice.filter.SelfProfileFilter;
import com.gmail.burinigor7.apigatewayservice.filter.UnauthorizedExceptionHandlerFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/moderation/**", "/tech-support/start-support",
                        "/tech-support/all-chats", "/tech-support/unassigned-chats", "/user/piece",
                        "/user/investments/**")
                .hasRole("ADMIN")
                .antMatchers("/auth", "/register", "/fundraising-projects/**", "/payout-requests/**",
                        "/api/fundraising-projects/moderation-files/**", "/api/fundraising-projects/image-files/**",
                        "/api/fundraising-projects/other-files/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .cors().disable();
    }

    @Bean
    public FilterRegistrationBean<UnauthorizedExceptionHandlerFilter> someFilterRegistration() {
        FilterRegistrationBean<UnauthorizedExceptionHandlerFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new UnauthorizedExceptionHandlerFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }
}
