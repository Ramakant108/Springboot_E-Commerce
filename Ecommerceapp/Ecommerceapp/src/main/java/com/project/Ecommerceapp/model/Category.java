package com.project.Ecommerceapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long C_Id;
    @NotBlank(message ="name should contail two char")
    private String categoryname;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;


//    public Category(Long c_Id, String c_Name) {
//        this.C_Id=c_Id;
//        this.C_Name=c_Name;
//    }
//    public Category() {
//    }
//    public void setC_Id(Long c_Id) {
//        C_Id = c_Id;
//    }
//    public Long getC_Id() {
//        return C_Id;
//    }
//
//    public void setC_Name(String c_Name) {
//        C_Name = c_Name;
//    }
//    public String getC_Name() {
//        return C_Name;
//    }




}
