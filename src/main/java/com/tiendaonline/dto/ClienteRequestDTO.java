package com.tiendaonline.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClienteRequestDTO {
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String direccion;
}
