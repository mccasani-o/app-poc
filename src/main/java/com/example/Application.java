package com.example;

import com.example.model.entity.TipoCambio;
import com.example.repository.TipoCambioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private final TipoCambioRepository tipoCambioRepository;
	private final DatabaseClient databaseClient;

	public Application(TipoCambioRepository tipoCambioRepository, DatabaseClient databaseClient) {
		this.tipoCambioRepository = tipoCambioRepository;
		this.databaseClient = databaseClient;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {
		crearTablaSiNoExiste().thenMany(
						tipoCambioRepository.save(new TipoCambio(null, "USD", "PEN", new BigDecimal("3.66")))
								.then(tipoCambioRepository.save(new TipoCambio(null, "PEN", "USD", new BigDecimal("1.18"))))
								.thenMany(tipoCambioRepository.findAll())
				).doOnNext(tipoCambio -> System.out.println("TipoCambio: " + tipoCambio))
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
}

