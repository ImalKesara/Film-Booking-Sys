package com.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    @Column(unique = true , nullable = false)
    private String email;
    private String phone;
    @Column(nullable = false)
    private String password;
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private LoyaltyPoint loyaltyPoint;

    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    public UserRole getRole() {
        return this.userRole; // Verify `role` contains valid UserRole
    }

}

