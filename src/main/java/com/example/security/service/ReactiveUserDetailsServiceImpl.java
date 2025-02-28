package com.example.security.service;

import com.example.security.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public ReactiveUserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return usuarioRepository.findByNombreUsuario(username)
                .map(user -> User.withUsername(user.getNombreUsuario())
                        .password(user.getClave())  // Debería estar encriptada
                        .roles("USER") // Asigna roles si es necesario
                        .build());
    }
}
