package com.example.security.service;

import com.example.security.model.dto.LoginRequest;
import com.example.security.model.dto.LoginResponse;
import com.example.security.model.dto.UsuarioDto;
import reactor.core.publisher.Mono;

public interface LoginService {



    Mono<Void> crearUsuario(UsuarioDto usuarioDto);
    Mono<LoginResponse> login(LoginRequest request);
}
