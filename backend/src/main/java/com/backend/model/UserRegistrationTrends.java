package com.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "v_user_registration_trends")
@IdClass(UserRegistrationTrendsId.class)
public class UserRegistrationTrends {
    @Id
    private LocalDate registrationDate;
    @Id
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Long newRegistrations;
    private Long cumulativeUsers;
}