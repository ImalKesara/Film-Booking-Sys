package com.backend.security.dto;

import com.backend.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userId;
    private String name;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private UserRole userRole;

}
