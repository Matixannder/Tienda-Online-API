package com.tiendaonline.mapper;

import com.tiendaonline.dto.ClienteRequestDTO;
import com.tiendaonline.dto.ClienteResponseDTO;
import com.tiendaonline.model.Cliente;

public class ClienteMapper {

    public static Cliente toEntity(ClienteRequestDTO dto) {
        return new Cliente(
                dto.getNombre(),
                dto.getApellido(),
                dto.getCorreo(),
                dto.getTelefono(),
                dto.getDireccion()
        );
    }

    public static ClienteResponseDTO toResponse(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getCorreo()
        );
    }
}
