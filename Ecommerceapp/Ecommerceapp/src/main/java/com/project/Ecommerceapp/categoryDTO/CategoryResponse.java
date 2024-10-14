package com.project.Ecommerceapp.categoryDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private List<CategoryDTO> categoryDTOList;
    private Integer PageNumber;
    private Integer PageSize;
    private Integer TotalElement;
    private Integer totalpage;
    private boolean currentpage;

}
