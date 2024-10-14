package com.project.Ecommerceapp.securityservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.Ecommerceapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImp implements UserDetails {

     private Long Id;
     private String userName;
     private String email;

     @JsonIgnore
     private String password;
     private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public static UserDetailsImp build(User user){
        List<GrantedAuthority> authorities=user.getRole().stream().map(role->new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
        return new UserDetailsImp(user.getUser_ID(),user.getUserName(),user.getEmail(),user.getPassword(),authorities);
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    public boolean isEnabled() {
        return true;
    }

}
