package com.project.Ecommerceapp.controller;


import com.project.Ecommerceapp.Securityjwt.JwtUtils;
import com.project.Ecommerceapp.model.Approle;
import com.project.Ecommerceapp.model.Role;
import com.project.Ecommerceapp.model.User;
import com.project.Ecommerceapp.repostory.RoleRepository;
import com.project.Ecommerceapp.repostory.UserRepostory;
import com.project.Ecommerceapp.securityconfiguration.request.LoginRequest;
import com.project.Ecommerceapp.securityconfiguration.request.SignupRequest;
import com.project.Ecommerceapp.securityconfiguration.response.LoginResponse;
import com.project.Ecommerceapp.securityconfiguration.response.UserInfoResponse;
import com.project.Ecommerceapp.securityservice.UserDetailsImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
public class Authcontroller {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepostory userRepostory;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/api/auth/signin")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest){
        Authentication authentication;
        try {
           authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

        } catch (AuthenticationException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImp userDetailsImp=(UserDetailsImp) authentication.getPrincipal();
        ResponseCookie jwttoken=jwtUtils.generateJwtCookies(userDetailsImp);
        List<String> role=userDetailsImp.getAuthorities( ).stream().map(item->item.getAuthority()).collect(Collectors.toList());
        LoginResponse response=new LoginResponse(userDetailsImp.getId(),userDetailsImp.getUsername(),role);

        return  ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, String.valueOf(jwttoken)).body(response);
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<?> singup(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepostory.existsByUserName(signupRequest.getUserName())) {
            return new ResponseEntity<>("this user name alredy exist", HttpStatus.BAD_REQUEST);
        }
        if (userRepostory.existsByEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>("this Email alredy exist", HttpStatus.BAD_REQUEST);
        }

        Set<String> roles = signupRequest.getRoles();
        Set<Role> Role = new HashSet<>();
        if (roles == null) {
            Role userRole = roleRepository.findByRoleName(Approle.USER_ROLE);
            if (userRole == null) {
                throw new RuntimeException("Role is not found");
            }
            Role.add(userRole);
        } else {
            roles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(Approle.ADMIN_ROLE);
                        if (adminRole == null) {
                            throw new RuntimeException("Role is not found");
                        }
                        Role.add(adminRole);
                        break;
                    case "seller":
                        Role sellerRole = roleRepository.findByRoleName(Approle.SELLER_ROLE);
                        if (sellerRole == null) {
                            throw new RuntimeException("Role is not found");
                        }
                        Role.add(sellerRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(Approle.USER_ROLE);
                        if (userRole == null) {
                            throw new RuntimeException("Role is not found");
                        }
                        Role.add(userRole);

                }
                ;
            });
        }
        User user = new User(signupRequest.getUserName(), signupRequest.getEmail(),encoder.encode(signupRequest.getPassword()));
        user.setRole(Role);
        userRepostory.save(user);
        return ResponseEntity.ok().body("user is added suseesfuly");

    }
    @GetMapping("/api/auth/username")
    public String username(Authentication authentication){
        if(authentication!=null){
            UserDetailsImp userDetailsImp=(UserDetailsImp)authentication.getPrincipal();
            return userDetailsImp.getUsername();
        }
        return "";
    }

    @GetMapping("/api/auth/userDetails")
    public ResponseEntity<?> userdetails(Authentication authentication){
        UserDetailsImp userDetailsImp= (UserDetailsImp) authentication.getPrincipal();
//      User user=userRepostory.findByUserName(userDetailsImp.getUsername());
        List<String> role=userDetailsImp.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());
        UserInfoResponse response=new UserInfoResponse(userDetailsImp.getId(),userDetailsImp.getUsername(),role);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/auth/signout")
    public ResponseEntity<?> signout(){
        ResponseCookie cookie=jwtUtils.getcleancookies();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, String.valueOf(cookie)).body("you signout successfuly");
    }

}
