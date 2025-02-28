package com.example.security.model.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String usuario;
    private String clave;
}
