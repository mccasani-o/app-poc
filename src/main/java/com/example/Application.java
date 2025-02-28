package com.example;

import com.example.model.entity.TipoCambio;
import com.example.repository.TipoCambioRepository;
import com.example.security.model.entity.Usuario;
import com.example.security.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final TipoCambioRepository tipoCambioRepository;
    private final UsuarioRepository usuarioRepository;
    private final DatabaseClient databaseClient;

    public Application(TipoCambioRepository tipoCambioRepository, UsuarioRepository usuarioRepository, DatabaseClient databaseClient) {
        this.tipoCambioRepository = tipoCambioRepository;
        this.usuarioRepository = usuarioRepository;
        this.databaseClient = databaseClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        // Asegurar que ambas tablas existen antes de realizar inserts
        crearTablaSiNoExiste()
                .then(crearTablaUsuarioSiNoExiste()) // <-- Asegurar que la tabla USUARIO existe
                .thenMany(tipoCambioRepository.save(new TipoCambio(null, "USD", "PEN", new BigDecimal("3.66"))))
                .then(tipoCambioRepository.save(new TipoCambio(null, "PEN", "USD", new BigDecimal("1.18"))))
                .thenMany(tipoCambioRepository.findAll())
                .doOnNext(tipoCambio -> System.out.println("TipoCambio: " + tipoCambio))
                .subscribe();

        crearTablaUsuarioSiNoExiste() // <-- Asegurar que la tabla existe antes de insertar
                .thenMany(usuarioRepository.save(new Usuario(null, "test", "12345", "ADMIN")))
                .thenMany(usuarioRepository.findAll())
                .doOnNext(usuario -> System.out.println("Usuario: " + usuario))
                .subscribe();
    }


    private Mono<Void> crearTablaSiNoExiste() {
        return databaseClient.sql("""
                    CREATE TABLE IF NOT EXISTS tipo_cambio (
                        id IDENTITY PRIMARY KEY,
                        moneda_origen VARCHAR(3) NOT NULL,
                        moneda_destino VARCHAR(3) NOT NULL,
                        tasa DECIMAL(10, 2) NOT NULL
                    )
                """).fetch().rowsUpdated().then();
    }

    private Mono<Void> crearTablaUsuarioSiNoExiste() {
        return databaseClient.sql("""
                    CREATE TABLE IF NOT EXISTS USUARIO (
                        id IDENTITY PRIMARY KEY,
                        nombre_usuario VARCHAR(30) NOT NULL,
                        clave VARCHAR(300) NOT NULL,
                        roles VARCHAR(30) NOT NULL
                    )
                """).fetch().rowsUpdated().then();
    }


}

