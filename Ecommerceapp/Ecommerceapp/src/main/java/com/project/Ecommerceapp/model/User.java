package com.project.Ecommerceapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Userinfo")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_ID;

    @NotBlank
    @Size(max = 20)
    private String userName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 160)
    private String password;

    public User(String userName,String email,String password) {
        this.userName =userName;
        this.email=email;
        this.password=password;
    }



    @ManyToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name ="Adresseofuser",
            joinColumns=@JoinColumn(name = "User_Id"),
            inverseJoinColumns=@JoinColumn(name = "Address_ID")
    )
    private List<Address> addressList;

    @ManyToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(name ="Roles",
               joinColumns=@JoinColumn(name = "User_Id"),
               inverseJoinColumns=@JoinColumn(name = "Role_Id")
               )
    private Set<Role> role;

    @OneToOne(mappedBy = "user" ,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Carts cart;
}
