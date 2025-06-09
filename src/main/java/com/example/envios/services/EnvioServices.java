package com.example.envios.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.envios.dto.EnvioDTO;
import com.example.envios.models.Envio;
import com.example.envios.repository.EnvioRepository;

@Service
public class EnvioServices {
    
    @Autowired
    private EnvioRepository envioRepository;

    public EnvioDTO guardar(EnvioDTO dto) {
        Envio cliente = toEntity(dto);
        Envio saved = envioRepository.save(cliente);
        return toDTO(saved);
    }

    public List<EnvioDTO> listar() {
        return envioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<EnvioDTO> obtenerPorId(Integer id) {
        return envioRepository.findById(id)
                .map(this::toDTO);
    }

    public Optional<EnvioDTO> actualizar(Integer id, EnvioDTO dto) {
        return envioRepository.findById(id).map(envio -> {
            envio.setDireccion(dto.getDireccion());
            envio.setEstado((dto.getEstado()));
            envio.setFecha_envio(dto.getFecha_envio());
            envio.setFecha_entregua(dto.getFecha_entregua());
            return toDTO(envioRepository.save(envio));
        });
    }

    public boolean eliminar(Integer id) {
        if (envioRepository.existsById(id)) {
            envioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Métodos auxiliares
    private EnvioDTO toDTO(Envio envio) {
        EnvioDTO dto = new EnvioDTO();
        dto.setId_envio(envio.getId());
        dto.setDireccion(envio.getDireccion());
        dto.setEstado(envio.getEstado());
        dto.setFecha_envio(envio.getFecha_envio());
        dto.setFecha_entregua(envio.getFecha_entregua());
        return dto;
    }

    private Envio toEntity(EnvioDTO dto) {
        Envio envio = new Envio();
        envio.setId(dto.getId_envio());
        envio.setEstado(dto.getEstado());
        envio.setFecha_envio(dto.getFecha_envio());
        envio.setFecha_entregua(dto.getFecha_entregua());
        return envio;
    }
    
}
