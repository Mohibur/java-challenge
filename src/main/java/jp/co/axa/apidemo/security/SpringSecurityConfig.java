package jp.co.axa.apidemo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Mohibur Rashid
 *
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    final String API_BASE = "/api/v1/employees";

    // Create 2 users for demo
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication() //
                .withUser("user").password("{noop}password").roles("USER") //
                .and() //
                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");

    }

    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.httpBasic() // HTTP Basic authentication
                .and() //
                .authorizeRequests() //
                .antMatchers(HttpMethod.GET, API_BASE + "/**").hasRole("USER") //
                .antMatchers(HttpMethod.POST, API_BASE).hasRole("ADMIN") //
                .antMatchers(HttpMethod.PUT, API_BASE + "/**").hasRole("ADMIN") //
                .antMatchers(HttpMethod.PATCH, API_BASE + "/**").hasRole("ADMIN") //
                .antMatchers(HttpMethod.DELETE, API_BASE + "/**").hasRole("ADMIN") //
                .and() //
                .csrf() //
                .disable() //
                .formLogin().disable();
    }
}