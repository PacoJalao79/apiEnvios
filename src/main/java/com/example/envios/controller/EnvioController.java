package com.example.envios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.envios.dto.EnvioDTO;
import com.example.envios.services.EnvioServices;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@RestController
@RequestMapping("/api/envio")
public class EnvioController {
    
    @Autowired
    private EnvioServices service;

    @PostMapping
    public ResponseEntity<EnvioDTO> crear(@RequestBody EnvioDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @GetMapping
    public ResponseEntity<List<EnvioDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvioDTO> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvioDTO> actualizar(@PathVariable Integer id, @RequestBody EnvioDTO dto) {
        return service.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return service.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    //METODO HATEOAS para buscar por ID
    @GetMapping("/hateoas/{id}")
    public ResponseEntity<EnvioDTO> obtenerHATEOAS(@PathVariable Integer id) {
        return service.obtenerPorId(id)
            .map(dto -> {
                // Agregar los links HATEOAS
                dto.add(linkTo(methodOn(EnvioController.class).obtenerHATEOAS(id)).withSelfRel());
                dto.add(linkTo(methodOn(EnvioController.class).obtenerTodosHATEOAS()).withRel("todos"));
                dto.add(linkTo(methodOn(EnvioController.class).eliminar(id)).withRel("eliminar"));

                dto.add(Link.of("http://localhost:8888/api/proxy/productos/" + dto.getId_envio()).withSelfRel());
                dto.add(Link.of("http://localhost:8888/api/proxy/productos/" + dto.getId_envio()).withRel("Modificar HATEOAS").withType("PUT"));
                dto.add(Link.of("http://localhost:8888/api/proxy/productos/" + dto.getId_envio()).withRel("Eliminar HATEOAS").withType("DELETE"));

                return ResponseEntity.ok(dto);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
}

    //METODO HATEOAS para listar todos los productos utilizando HATEOAS
    @GetMapping("/hateoas")
    public List<EnvioDTO> obtenerTodosHATEOAS() {
        List<EnvioDTO> lista = service.listar();

        for (EnvioDTO dto : lista) {
            //link url de la misma API
            dto.add(linkTo(methodOn(EnvioController.class).obtenerHATEOAS(dto.getId_envio())).withSelfRel());

            //link HATEOAS para API Gateway "A mano"
            dto.add(Link.of("http://localhost:8888/api/proxy/productos").withRel("Get todos HATEOAS"));
            dto.add(Link.of("http://localhost:8888/api/proxy/productos/" + dto.getId_envio()).withRel("Crear HATEOAS").withType("POST"));
        }

        return lista;
    }

}
