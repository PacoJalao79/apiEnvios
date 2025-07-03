package com.example.envios.dto;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDTO extends RepresentationModel<EnvioDTO>{

    private Integer id_envio;
    private Integer id_venta;
    private String direccion;
    private String estado;
    private LocalDateTime fecha_envio;
    private LocalDateTime fecha_entregua;
    
}
