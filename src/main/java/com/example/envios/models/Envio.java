package com.example.envios.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "envios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_envio")
    private Integer id_envio;

    private Integer id_venta;
    private String direccion;
    private String estado;
    private LocalDateTime fecha_envio;
    private LocalDateTime fecha_entregua;
    
}

