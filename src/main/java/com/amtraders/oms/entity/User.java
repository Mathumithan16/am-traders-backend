package com.amtraders.oms.entity;

import com.amtraders.oms.enums.Role;
import com.amtraders.oms.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor //automatically generates a no-argument constructor (a constructor with no parameters)
@AllArgsConstructor
@Builder //Lombok annotation that implements the Builder Design Pattern, making it easy to create objects with many fields without writing multiple constructors.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 10)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;
}
