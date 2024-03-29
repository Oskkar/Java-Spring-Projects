package com.example.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private UserDetailsService userDetailsService;
  
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
      return new BCryptPasswordEncoder();
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception{
    http.csrf().ignoringAntMatchers("/h2console/**");
    http.headers().frameOptions().sameOrigin();
    
    http
      .authorizeRequests()
      	.antMatchers("/resources/**","/loginError","/registration","/h2console/**").permitAll()
      	.antMatchers("/admin/**").hasAuthority("ADMIN")
        .antMatchers("/user/**").hasAuthority("USER")
        .anyRequest().authenticated()
        .and()
      .formLogin()
        .loginPage("/login")
        .successHandler(new CustomAuthenticationSuccess())
        .failureHandler(new CustomAuthenticationFailure()) 
        .permitAll()
        .and()
      .logout()
        .permitAll()
        .and()
       .exceptionHandling().accessDeniedPage("/403"); 
  }
  
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
      auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
  }  
}
