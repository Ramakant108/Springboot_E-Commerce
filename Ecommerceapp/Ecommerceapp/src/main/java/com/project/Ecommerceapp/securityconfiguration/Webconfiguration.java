package com.project.Ecommerceapp.securityconfiguration;

import com.project.Ecommerceapp.Securityjwt.AuthEntryPointJwt;
import com.project.Ecommerceapp.Securityjwt.AuthTokenFilter;
import com.project.Ecommerceapp.model.Approle;
import com.project.Ecommerceapp.model.Role;
import com.project.Ecommerceapp.model.User;
import com.project.Ecommerceapp.repostory.RoleRepository;
import com.project.Ecommerceapp.repostory.UserRepostory;
import com.project.Ecommerceapp.securityservice.UsrDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;

@Configuration
@EnableWebSecurity

public class Webconfiguration {

    @Bean
    public UsrDetailsServiceImp userDetailsService(){
        return new UsrDetailsServiceImp();
    }

    @Autowired
    private AuthEntryPointJwt authEntryPointJwt;

   @Bean
    public AuthTokenFilter authTokenFilter(){
       return new AuthTokenFilter();
   }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authprovider=new DaoAuthenticationProvider();
        authprovider.setPasswordEncoder(bCryptPasswordEncoder());
        authprovider.setUserDetailsService(userDetailsService());
        return authprovider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain webSecurity(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(session->session.sessionCreationPolicy((SessionCreationPolicy.STATELESS)));

        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/public/**").permitAll()
//                        .requestMatchers("/api/admin/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/api/test/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
//                        .requestMatchers("/cart/products/**").permitAll()
                        .anyRequest().authenticated());
        http.exceptionHandling(exeption->exeption.authenticationEntryPoint(authEntryPointJwt));
        http.headers(header->header.frameOptions(frameOptions->frameOptions.sameOrigin()));
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(daoAuthenticationProvider());
        return http.build();
    }


    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepostory userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Retrieve or create roles
            Role userRole = roleRepository.findByRoleName(Approle.USER_ROLE);
                    if(userRole==null){
                        Role newUserRole = new Role(Approle.USER_ROLE);
                        userRole = roleRepository.save(newUserRole);
                    }

            Role sellerRole = roleRepository.findByRoleName(Approle.SELLER_ROLE);
                    if(sellerRole==null) {
                        Role newSellerRole = new Role(Approle.SELLER_ROLE);
                        sellerRole =roleRepository.save(newSellerRole);
                    }

            Role adminRole = roleRepository.findByRoleName(Approle.ADMIN_ROLE);
                    if(adminRole==null) {
                        Role newAdminRole = new Role(Approle.ADMIN_ROLE);
                        adminRole = roleRepository.save(newAdminRole);
                    }

            Set<Role> userRoles = Set.of(userRole);
            Set<Role> sellerRoles = Set.of(sellerRole);
            Set<Role> adminRoles = Set.of(userRole, sellerRole, adminRole);


            // Create users if not already present
            if (!userRepository.existsByUserName("user1")) {
                User user1 = new User("user1", "user1@example.com", passwordEncoder.encode("password1"));
                userRepository.save(user1);
            }

            if (!userRepository.existsByUserName("seller1")) {
                User seller1 = new User("seller1", "seller1@example.com", passwordEncoder.encode("password2"));
                userRepository.save(seller1);
            }

            if (!userRepository.existsByUserName("admin")) {
                User admin = new User("admin", "admin@example.com", passwordEncoder.encode("adminPass"));
                userRepository.save(admin);
            }

            // Update roles for existing users
            User user1=userRepository.findByUserName("user1");
            if(user1!=null) {
                user1.setRole(userRoles);
                userRepository.save(user1);
            }

            User seller1=userRepository.findByUserName("seller1");
            if(seller1!=null){
                seller1.setRole(sellerRoles);
                userRepository.save(seller1);
            }

            User admin1=userRepository.findByUserName("admin");
            if(admin1!=null){
                admin1.setRole(adminRoles);
                userRepository.save(admin1);
            }
        };
    }

}
