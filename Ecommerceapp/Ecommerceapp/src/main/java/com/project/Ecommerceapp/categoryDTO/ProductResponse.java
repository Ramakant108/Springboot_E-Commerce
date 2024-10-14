package com.project.Ecommerceapp.categoryDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    public ProductResponse(List<ProductDTO> productResponse) {
        this.productResponse = productResponse;
    }

    private List<ProductDTO>  productResponse;
    private Integer pagesize;
    private Integer pagenumber;
    private Integer totalpages;
    private Long totalelments;
    private boolean islastpage;


}
