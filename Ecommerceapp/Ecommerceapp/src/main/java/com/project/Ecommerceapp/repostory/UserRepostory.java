package com.project.Ecommerceapp.repostory;

import com.project.Ecommerceapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepostory extends JpaRepository<User,Long> {
    User findByUserName(String username);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);
}
