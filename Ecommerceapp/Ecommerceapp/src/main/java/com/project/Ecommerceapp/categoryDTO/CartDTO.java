package com.project.Ecommerceapp.categoryDTO;

import com.project.Ecommerceapp.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long cartID;
    private double totalprice=0.0;
    private List<ProductDTO> products=new ArrayList<>();
}
