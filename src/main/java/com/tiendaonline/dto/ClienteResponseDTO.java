package com.tiendaonline.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponseDTO {

    private Long id;
    private String nombre;
    private String correo;

    public ClienteResponseDTO() {}

    public ClienteResponseDTO(Long id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
    }
}
