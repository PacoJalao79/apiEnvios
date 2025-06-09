package com.example.envios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.envios.models.Envio;

public interface EnvioRepository extends JpaRepository<Envio, Integer> {
}
