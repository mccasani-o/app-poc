package com.example.security.service;

import com.example.security.model.dto.LoginRequest;
import com.example.security.model.dto.LoginResponse;
import com.example.security.model.dto.UsuarioDto;
import com.example.security.model.entity.Usuario;
import com.example.security.repository.UsuarioRepository;
import com.example.security.util.JwtUtil;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ReactiveUserDetailsService userDetailsService;

    public LoginServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, ReactiveUserDetailsService userDetailsService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Void> crearUsuario(UsuarioDto usuarioDto) {
        return this.usuarioRepository
                .save(Usuario.builder()
                        .nombreUsuario(usuarioDto.getNombreUsuario())
                        .clave(this.passwordEncoder.encode(usuarioDto.getClave()))
                        .roles("ADMIN")
                        .build())

                .then();
    }


    @Override
    public Mono<LoginResponse> login(LoginRequest request) {
        return userDetailsService.findByUsername(request.getUsuario())
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                .flatMap(user -> {
                    if (passwordEncoder.matches(request.getClave(), user.getPassword())) {
                        // Verifica si `jwtUtil.generateToken` es reactivo o bloqueante
                        return Mono.fromCallable(() -> jwtUtil.generateToken(user, generateExtraClaims(user)))
                                .map(LoginResponse::new);
                    }
                    return Mono.error(new RuntimeException("Credenciales inválidas"));
                });
    }

    private Map<String, Object> generateExtraClaims(UserDetails user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getUsername());
        extraClaims.put("authorities", user.getAuthorities());
        return extraClaims;
    }

}
