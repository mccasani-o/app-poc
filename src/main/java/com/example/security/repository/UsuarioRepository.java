package com.example.security.repository;

import com.example.security.model.entity.Usuario;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UsuarioRepository extends ReactiveCrudRepository<Usuario, Long> {

    Mono<Usuario> findByNombreUsuario(String nombreUsuario);
}
