package com.project.Ecommerceapp.securityservice;

import com.project.Ecommerceapp.model.User;
import com.project.Ecommerceapp.repostory.UserRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UsrDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepostory userRepostory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepostory.findByUserName(username);
        if(user==null){
            throw new UsernameNotFoundException("user not fount with username"+username);
        }
        return UserDetailsImp.build(user);
    }
}
