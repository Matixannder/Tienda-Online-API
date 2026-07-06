package com.tiendaonline.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private Integer stock;
}
