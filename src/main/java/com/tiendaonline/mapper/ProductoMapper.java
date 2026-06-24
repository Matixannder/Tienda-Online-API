package com.tiendaonline.mapper;

import com.tiendaonline.dto.ProductoRequestDTO;
import com.tiendaonline.dto.ProductoResponseDTO;
import com.tiendaonline.model.Producto;

public class ProductoMapper {

    public static Producto toEntity(ProductoRequestDTO dto) {
        return new Producto(
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getPrecio(),
                dto.getStock(),
                dto.getCategoria()
        );
    }

    public static ProductoResponseDTO toResponse(Producto producto) {
        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getStock()
        );
    }
}
