package com.backend.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PieVisualizeDto {
    private Long id;
    private String hall;
    private int movies;
}