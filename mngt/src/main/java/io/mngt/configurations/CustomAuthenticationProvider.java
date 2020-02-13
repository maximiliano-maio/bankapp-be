package io.mngt.configurations;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.mngt.entity.User;
import io.mngt.repositories.UserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository ur;

    @Override
    public Authentication authenticate(Authentication arg0) throws AuthenticationException {
        String username = arg0.getName();
        Object credential = arg0.getCredentials();
        if(!(credential instanceof String)) return null;

        String password = credential.toString();

        List<User> list = new ArrayList<>();
        ur.findAll().forEach(list::add);

        Optional<User> userOptional = list.stream().filter(u -> u.match(username, password)).findFirst(); 

        if(!userOptional.isPresent()) return null;

        List<GrantedAuthority> grantedUsers = new ArrayList<>();
        grantedUsers.add(new SimpleGrantedAuthority(userOptional.get().getRole()));

        Authentication auth = new UsernamePasswordAuthenticationToken(username, password, grantedUsers);
        return auth;


        
    }

    @Override
    public boolean supports(Class<?> arg0) {
        
        return arg0.equals(UsernamePasswordAuthenticationToken.class);
    }

    
}