package com.tiendaonline.service;

import com.tiendaonline.dto.ProductoRequestDTO;
import com.tiendaonline.dto.ProductoResponseDTO;
import com.tiendaonline.mapper.ProductoMapper;
import com.tiendaonline.model.Producto;
import com.tiendaonline.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }


    public Optional<ProductoResponseDTO> crear(ProductoRequestDTO dto) {
        if (this.productoRepository.existsByNombre(dto.getNombre())) {
            return Optional.empty();
        }

        Producto producto = ProductoMapper.toEntity(dto);
        producto = this.productoRepository.save(producto);
        return Optional.of(ProductoMapper.toResponse(producto));
    }

    public List<ProductoResponseDTO> listar() {
        return this.productoRepository.findAll()
                .stream()
                .map(ProductoMapper::toResponse)
                .toList();
    }

public ProductoResponseDTO buscarPorId(Long id) {
        Producto producto = this.productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        return ProductoMapper.toResponse(producto);
    }

    public void eliminar(Long id) {
        if (!this.productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
        this.productoRepository.deleteById(id);
    }
}
