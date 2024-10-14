package com.project.Ecommerceapp.categoryDTO;

import com.project.Ecommerceapp.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private String discription;
    private Integer quantity;
    private String image;
    private double price;
    private double discount;
    private double speciaprice;
    private Category category;
}
