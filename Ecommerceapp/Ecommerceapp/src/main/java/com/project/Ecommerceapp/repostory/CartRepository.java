package com.project.Ecommerceapp.repostory;

import com.project.Ecommerceapp.model.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Carts,Long> {
    @Query("SELECT c FROM Carts c WHERE c.user.email = ?1")
    Carts findByEmail(String email);

    @Query("SELECT c FROM Carts c WHERE c.user.email=?1 AND c.Id=?2")
    Carts findByEmailAndCartId(String emailId, Long cartId);


}
