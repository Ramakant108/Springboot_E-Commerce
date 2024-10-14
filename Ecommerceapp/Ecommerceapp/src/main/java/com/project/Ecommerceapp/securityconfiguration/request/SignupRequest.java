package com.project.Ecommerceapp.securityconfiguration.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;
    private Set<String> roles;

}
