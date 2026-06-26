package com.tiendaonline.controller;

import com.tiendaonline.dto.ProductoRequestDTO;
import com.tiendaonline.dto.ProductoResponseDTO;
import com.tiendaonline.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@RequestBody ProductoRequestDTO dto) {
        if (dto.getStock() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Optional<ProductoResponseDTO> response = this.productoService.crear(dto);

        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response.get());
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        return ResponseEntity.ok(this.productoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(this.productoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        this.productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
