package com.example.security.controller;

import com.example.security.model.dto.LoginRequest;
import com.example.security.model.dto.LoginResponse;
import com.example.security.model.dto.UsuarioDto;
import com.example.security.service.LoginService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final LoginService usuarioService;

    public AuthController(LoginService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    @ResponseStatus(CREATED)
    public Mono<Void> registrarUsuario(@RequestBody UsuarioDto request) {
       return this.usuarioService.crearUsuario(request);

    }

    @PostMapping("/login")
    public Mono<LoginResponse> login(@RequestBody LoginRequest request) {
        return this.usuarioService.login(request);
    }


}
