package com.project.Ecommerceapp.helpingpakage;


import com.project.Ecommerceapp.exaption.DataNotPresentExeption;
import com.project.Ecommerceapp.model.User;
import com.project.Ecommerceapp.repostory.UserRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AuthUtils {

     @Autowired
     UserRepostory userRepostory;

     public String loggedInEmail(){
          Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
          User user=userRepostory.findByUserName(authentication.getName());
          if(user==null){
               throw new DataNotPresentExeption("user not present in repository");
          }
          return user.getEmail();
     }

     public User loggedInUser(){
          Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
          User user=userRepostory.findByUserName(authentication.getName());
          if(user==null){
               throw new DataNotPresentExeption("user not present in repository");
          }
          return user;
     }


}
