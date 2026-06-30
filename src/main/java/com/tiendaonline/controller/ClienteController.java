package com.tiendaonline.controller;

import java.util.List;
import java.util.Optional;

import com.tiendaonline.dto.ClienteRequestDTO;
import com.tiendaonline.dto.ClienteResponseDTO;
import com.tiendaonline.service.ClienteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            throw new ErrorResponseException(HttpStatus.CONFLICT, new Exception("Usuario ya ha sido creado"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response.get());
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        return ResponseEntity.ok(this.clienteService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND, new Exception("Id introducido es invalido"));
        }

        try {
            return ResponseEntity.ok(this.clienteService.buscarPorId(id));
        } catch (RuntimeException e) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND, e);
        }
    }

    @GetMapping("/{correo}")
    public ResponseEntity<ClienteResponseDTO> buscarPorCorreo(@PathVariable String correo) {
        boolean isValidEmailFormat = correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
        if (correo == null || !isValidEmailFormat) {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST, new Exception("Correo introducido no tiene un formato valido"));
        }

        try {
            return ResponseEntity.ok(this.clienteService.buscarPorCorreo(correo));
        } catch (RuntimeException e) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND, e);
        }
    }

    @GetMapping("/{telefono}")
    public ResponseEntity<ClienteResponseDTO> buscarPorTelefono(@PathVariable String telefono) {
        boolean isValidPhoneNumberFormat = telefono.matches("^\\+?\\d{7,15}$");
        if (telefono == null || !isValidPhoneNumberFormat) {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST, new Exception("Numero telefonico introducido no es de un formato valido"));
        }

        try {
            return ResponseEntity.ok(this.clienteService.buscarPorTelefono(telefono));
        } catch (RuntimeException e) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND, e);
        }
    }
}
