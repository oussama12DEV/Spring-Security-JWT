package com.secu.springsec3.Service;

import com.secu.springsec3.Entite.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDatailesServicIMPL implements UserDetailsService {
    private ServiceSec serviceSec;

    public UserDatailesServicIMPL(ServiceSec serviceSec) {
        this.serviceSec = serviceSec;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users user = serviceSec.LoadUserbyUsername(s);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
            user.getListRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRole()));
        });
        return new User(user.getUsername(),user.getPassword(),authorities);
    }
}
