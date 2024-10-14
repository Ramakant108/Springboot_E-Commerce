package com.project.Ecommerceapp.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_Id")
    private Integer role_Id;

    @Enumerated(EnumType.STRING)
    @Column(name = "RoleName")
    private Approle roleName;

    public Role(Approle roleName) {
        this.roleName = roleName;
    }

//    @ManyToMany(mappedBy = "role")
//    private Set<User> users;


}
