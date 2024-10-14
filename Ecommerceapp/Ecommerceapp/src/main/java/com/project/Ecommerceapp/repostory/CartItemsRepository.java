package com.project.Ecommerceapp.repostory;

import com.project.Ecommerceapp.model.CartItems;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems,Long> {
    @Query("SELECT ci FROM CartItems ci WHERE ci.product.id = ?1 AND  ci.cart.id = ?2" )
    CartItems findByProductIdAndCartId(Long productId, Long cartId);

    @Query("SELECT ci FROM CartItems ci WHERE ci.cart.id = ?1")
    List<CartItems> findByCartId(Long cartId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItems ci WHERE ci.cart.id = ?1 AND ci.product.id = ?2")
    void deleteCartItemByProductIdAndCartId(Long cartId, Long productId);
}
