package com.project.Ecommerceapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long address_Id;

    @Column(name = "Streit")
    @Size(min = 7,message = "size will be minimum 7 character")
    private String streit;

    @Column(name = "Building")
    @Size(min = 5)
    private String building;

    @Column(name = "City")
    @Size(min = 2)
    private String city;

//    public Address(Long address_Id,String streit,String building,String city,String state,String contry,String pincode) {
//        this.state=state;
//        this.building=building;
//        this.city=city;
//        this.contry=contry;
//        this.pincode=pincode;
//        this.
//    }

    @Column(name = "State")
    @Size(min = 2)
    private String state;

    @Column(name = "Contry")
    @Size(min = 5)
    private String contry;

    @Column(name = "Pincode")
    @Size(min = 6)
    private String pincode;

    @ManyToMany(mappedBy ="addressList" )
    private Set<User> users;

}
