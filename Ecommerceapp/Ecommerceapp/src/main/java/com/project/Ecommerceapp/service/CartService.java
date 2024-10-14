package com.project.Ecommerceapp.service;

import com.project.Ecommerceapp.categoryDTO.CartDTO;
import com.project.Ecommerceapp.model.Carts;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CartService {
     CartDTO addproduct(Long productId,Integer quantity);
     List<CartDTO> getAllCart();

     CartDTO getUsercart(String emailId, Long cartId);


     @Transactional
     CartDTO updateProductQuantityInCart(Long productId, Integer quantity);

    String deleteProductFromCart(Long cartId, Long productId);
}
