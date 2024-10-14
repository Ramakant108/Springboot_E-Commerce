package com.project.Ecommerceapp.service;
import com.project.Ecommerceapp.categoryDTO.CategoryDTO;
import com.project.Ecommerceapp.categoryDTO.CategoryResponse;
import com.project.Ecommerceapp.model.Category;

import java.util.List;

public interface categorymethod {

    public CategoryResponse getcategory(Integer PageNumber,Integer PageSize);
    public void add(CategoryDTO categoryDTO);
    public CategoryDTO delete(Long id);
    public String update(CategoryDTO categoryDTO,Long ID);
}
