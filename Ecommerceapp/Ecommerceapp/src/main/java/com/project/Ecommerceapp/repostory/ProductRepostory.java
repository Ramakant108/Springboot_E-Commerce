package com.project.Ecommerceapp.repostory;

import com.project.Ecommerceapp.model.Category;
import com.project.Ecommerceapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepostory extends JpaRepository<Product,Long> {

    Page<Product> findByCategory(Category category, Pageable productpage);

    Page<Product> findByProductNameLikeIgnoreCase(String s, Pageable productpage);
}
