package com.project.Ecommerceapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @NotBlank(message = "product name should not be null")
    @Size(min = 2,message = "it should contain tow character")
    private String productName;

    @NotBlank(message = "Discription is about your product it not be empty")
    private String discription;
    private Integer quantity;
    private String image;
    private double price;
    private double discount;
    private double speciaprice;

    @ManyToOne
    @JoinColumn(name = "category_Id")
    @JsonIgnore
    private Category category;
}
