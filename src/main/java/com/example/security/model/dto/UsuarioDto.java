package com.example.security.model.dto;

import lombok.Data;

@Data
public class UsuarioDto {
    private Long id;
    private String nombreUsuario;
    private String clave;
    private String roles;
}
