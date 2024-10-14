package com.project.Ecommerceapp.securityconfiguration.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserInfoResponse {
    private Long Id;
    private String userName;
    private List<String> roles;

    public UserInfoResponse(Long Id,String userName,List<String> roles){
        this.Id=Id;
        this.userName=userName;
        this.roles=roles;
    }
}
