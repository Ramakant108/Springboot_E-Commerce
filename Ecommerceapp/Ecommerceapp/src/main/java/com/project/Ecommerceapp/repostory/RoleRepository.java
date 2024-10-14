package com.project.Ecommerceapp.repostory;

import com.project.Ecommerceapp.model.Approle;
import com.project.Ecommerceapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(Approle approle);
}
