package com.secu.springsec3.Security;

import com.secu.springsec3.Entite.Users;
import com.secu.springsec3.Filter.JwtAuthenticationFilter;
import com.secu.springsec3.Filter.JwtAuthorizationFilter;
import com.secu.springsec3.Service.ServiceSec;
import com.secu.springsec3.Service.UserDatailesServicIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfiger extends WebSecurityConfigurerAdapter {
    @Autowired
    private ServiceSec serviceSec;
    @Autowired
    private UserDatailesServicIMPL userDetailsService;

    public SecurityConfiger(UserDatailesServicIMPL userDetailsService)
    {
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //pour statfull
        /* se code la je le fait dans la class UserDetailesServiceIMP
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                Users user = serviceSec.LoadUserbyUsername(s);
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                user.getListRoles().forEach(r->{
                    authorities.add(new SimpleGrantedAuthority(r.getRole()));
                });
                return new User(user.getUsername(),user.getPassword(),authorities);
            }
        });*/
     auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      //  http.formLogin();
        http.headers().frameOptions().disable();
        http.csrf().disable();

         //pour stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/h2-console/**","/RefrechToken/**").permitAll();
        // http.authorizeRequests().antMatchers(HttpMethod.GET,"/users/**").hasAuthority("USER");
        //http.authorizeRequests().antMatchers(HttpMethod.POST,"/users/**").hasAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new JwtAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);
    }
@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
