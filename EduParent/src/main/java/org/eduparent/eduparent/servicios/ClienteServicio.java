package org.eduparent.eduparent.servicios;

import java.util.List;
import java.util.Optional;
import org.eduparent.eduparent.entidades.Cliente;
import org.eduparent.eduparent.repositorios.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> getClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente saveOrUpdateCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    // Nuevo método para validar por dni y contraseña
    public Optional<Cliente> validarClientePorDni(String dni, String contraseña) {
        return clienteRepository.findByDniAndContraseña(dni, contraseña);
    }

    // Por si luego lo quieres usar en validaciones
    public Optional<Cliente> findClienteByDni(String dni) {
        return clienteRepository.findByDni(dni);
    }
}
