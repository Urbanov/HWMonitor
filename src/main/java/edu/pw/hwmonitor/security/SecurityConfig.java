package edu.pw.hwmonitor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;

import javax.sql.DataSource;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public SecurityManager securityManager() {
        return new SecurityManager();
    }

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/css/**", "/index").permitAll()
            .antMatchers("/403").permitAll()
            .antMatchers("/user/**").hasRole("USER")
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/common/**").hasAnyRole("USER", "ADMIN")
            .and()
            .formLogin().loginPage("/login").failureUrl("/login-error").successHandler((request, response, auth) -> {
                DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
                String role = securityManager().getRoles().toString();
                if (role.contains("ADMIN")) {
                    redirectStrategy.sendRedirect(request, response, "/admin/companies");
                }
                else {
                    redirectStrategy.sendRedirect(request, response, "/user/data");
                }
            })
            .and()
            .exceptionHandling().accessDeniedPage("/access-denied")
            .and()
            .csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
            .passwordEncoder(new BCryptPasswordEncoder())
            .usersByUsernameQuery("select username, password, enabled from users where username=?")
            .authoritiesByUsernameQuery("select username, role from user_roles where username=?");
    }
}