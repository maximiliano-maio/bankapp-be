package io.mngt.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.mngt.configurations.principal.UserPrincipal;
import io.mngt.entity.User;
import io.mngt.exceptions.NotFoundException;
import io.mngt.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user == null) throw new NotFoundException("Username doesn't exist");

        return new UserPrincipal(user);
    }
    
}