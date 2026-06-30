package com.tiendaonline.service;

import java.util.List;
import java.util.Optional;

import com.tiendaonline.dto.ClienteRequestDTO;
import com.tiendaonline.dto.ClienteResponseDTO;
import com.tiendaonline.mapper.ClienteMapper;
import com.tiendaonline.model.Cliente;
import com.tiendaonline.repository.ClienteRepository;

import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    public Optional<ClienteResponseDTO> crear(ClienteRequestDTO dto) {
        if (this.clienteRepository.existsByCorreoOrDireccionOrTelefono(
            dto.getCorreo(), dto.getDireccion(), dto.getTelefono()
        )) {
            return Optional.empty();
        }

        Cliente cliente = ClienteMapper.toEntity(dto);
        cliente = this.clienteRepository.save(cliente);
        return Optional.of(ClienteMapper.toResponse(cliente));
    }

    public List<ClienteResponseDTO> listar() {
        return this.clienteRepository.findAll()
                .stream()
                .map(ClienteMapper::toResponse)
                .toList();
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = this.clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
        return ClienteMapper.toResponse(cliente);
    }

    public ClienteResponseDTO buscarPorCorreo(String correo) {
        Cliente cliente = this.clienteRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Correo no encontrado: " + correo));

        return ClienteMapper.toResponse(cliente);
    }

    public ClienteResponseDTO buscarPorTelefono(String telefono) {
        Cliente cliente = this.clienteRepository.findByTelefono(telefono)
                .orElseThrow(() -> new RuntimeException("Ningun usuario esta vinculado al numero: " + telefono));

        return ClienteMapper.toResponse(cliente);
    }
}
