package com.project.Ecommerceapp.controller;

import com.project.Ecommerceapp.categoryDTO.CartDTO;
import com.project.Ecommerceapp.helpingpakage.AuthUtils;
import com.project.Ecommerceapp.model.Carts;
import com.project.Ecommerceapp.repostory.CartRepository;
import com.project.Ecommerceapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthUtils authUtils;

    @Autowired
    private CartRepository cartRepository;


    @PostMapping("/api/cartaddproduct/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addproductincart(@PathVariable Long productId, @PathVariable Integer quantity){
        CartDTO cartDTO=cartService.addproduct(productId,quantity);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }

    @GetMapping("/api/public/getallCarts")
    public ResponseEntity<List<CartDTO>> getallcart(){
        return new ResponseEntity<>(cartService.getAllCart(),HttpStatus.OK);
    }

    @GetMapping("/api/Users/carts")
    public ResponseEntity<CartDTO> getUserCart(){
        String emailId=authUtils.loggedInEmail();
        Carts carts=cartRepository.findByEmail(emailId);
        Long cartId=carts.getCartId();
        CartDTO cartDTO=cartService.getUsercart(emailId,cartId);
        return new ResponseEntity<>(cartDTO,HttpStatus.OK);

    }
    @PutMapping("/api/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId,
                                                     @PathVariable String operation) {

        CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,
                operation.equalsIgnoreCase("delete") ? -1 : 1);

        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
    }

    @DeleteMapping("/api/carts/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId,
                                                        @PathVariable Long productId) {
        String status = cartService.deleteProductFromCart(cartId, productId);

        return new ResponseEntity<String>(status, HttpStatus.OK);
    }
}
