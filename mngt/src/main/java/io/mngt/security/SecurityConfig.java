package io.mngt.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import io.mngt.configurations.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
@ComponentScan("io.mngt.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private CustomAuthenticationProvider customAuthProvider;

    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    //     http.httpBasic().and().authorizeRequests().anyRequest().authenticated().and().formLogin();
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll(); //.hasRole("ADMIN")//allow h2 console access to admins only
        http.requiresChannel().anyRequest().requiresSecure();
        http.authorizeRequests()//all other urls can be access by any authenticated role
            //.and().formLogin()//enable form login instead of basic login
            .and().csrf().ignoringAntMatchers("/h2-console/**")//don't apply CSRF protection to /h2-console
            .and().headers().frameOptions().sameOrigin();//allow use of frame to same origin urls
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthProvider);
        auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema().withUser("mem").password("mem").roles("ADMIN");
        // auth.inMemoryAuthentication().withUser("mem").password("mem").roles("ADMIN");
    }
    
}