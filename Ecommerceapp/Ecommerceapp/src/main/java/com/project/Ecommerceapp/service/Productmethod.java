package com.project.Ecommerceapp.service;

import com.project.Ecommerceapp.categoryDTO.ProductDTO;
import com.project.Ecommerceapp.categoryDTO.ProductResponse;
import com.project.Ecommerceapp.model.Product;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface Productmethod {
    public ProductDTO AddProduct(Product product, Long categoryId);

    ProductResponse getProduct(Integer pagenumber, Integer size, String Sortby, String sortOrder);

    public ProductDTO FindbyId(Long productId);

    ProductResponse getByCategory(Long categoryId, Integer pagenumber, Integer size, String Sortby, String sortOrder);


    ProductResponse getByKeyword(String keyword, Integer pagenumber, Integer size, String Sortby, String sortOrder);

    public ProductDTO updateProduct(Product product, Long p_id);
    public ProductDTO deleteProduct(Long Id);

    ProductDTO updateimage(Long id, MultipartFile image) throws IOException;
}
