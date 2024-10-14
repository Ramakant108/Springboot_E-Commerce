package com.project.Ecommerceapp.repostory;

import com.project.Ecommerceapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repostory extends JpaRepository<Category,Long> {


    Category findByCategoryname(String categoryname);
}
