package com.tiendaonline.controller;

import com.tiendaonline.dto.ClienteRequestDTO;
import com.tiendaonline.dto.ClienteResponseDTO;
import com.tiendaonline.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

// Los controllers no deberian guardar información porque esa tarea corresponde
// a la capa de la logica de negocio, cuya implementación queda fuera de la capa
// de negociación

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crear(@RequestBody ClienteRequestDTO dto) {
        Optional<ClienteResponseDTO> response = this.clienteService.crear(dto);

        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response.get());
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        return ResponseEntity.ok(this.clienteService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(this.clienteService.buscarPorId(id));
    }
}
