package com.project.Ecommerceapp.securityconfiguration.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private Long U_Id;

    @JsonIgnore
    private String jwtToken;

    private String username;
    private List<String> roles;

    public LoginResponse(Long U_Id,String username, List<String> roles) {
        this.U_Id=U_Id;
        this.username = username;
        this.roles = roles;
    }

}


