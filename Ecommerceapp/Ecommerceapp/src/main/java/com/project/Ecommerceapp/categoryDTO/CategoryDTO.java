package com.project.Ecommerceapp.categoryDTO;

import com.project.Ecommerceapp.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long C_Id;
    private  String categoryname;
    private List<Product> products;
}
