package com.example.envios.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDTO {

    private Integer id_envio;
    private String direccion;
    private String estado;
    private LocalDateTime fecha_envio;
    private LocalDateTime fecha_entregua;
    
}
