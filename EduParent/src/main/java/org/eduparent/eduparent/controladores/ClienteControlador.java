package org.eduparent.eduparent.controladores;

import java.util.List;
import java.util.Optional;
import org.eduparent.eduparent.entidades.Cliente;
import org.eduparent.eduparent.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RestController
@RequestMapping("/api/v1/clientes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteServicio;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteServicio.getAllClientes();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<?> registerCliente(@RequestBody Cliente cliente) {
        // Verificar si el DNI existe en administracion_alumno
        String sql = "SELECT COUNT(*) FROM administracion_alumno WHERE dni = :dni";
        Long count = (Long) entityManager.createNativeQuery(sql)
                .setParameter("dni", cliente.getDni())
                .getSingleResult();

        if (count == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El DNI no está registrado en la base de alumnos. No puede registrarse.");
        }

        // Verificar que ese DNI no esté ya registrado en cliente
        Optional<Cliente> existente = clienteServicio.findClienteByDni(cliente.getDni());
        if (existente.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El DNI ya tiene una cuenta registrada.");
        }

        Cliente savedCliente = clienteServicio.saveOrUpdateCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Cliente cliente) {
        Optional<Cliente> clienteValidado = clienteServicio.validarClientePorDni(cliente.getDni(), cliente.getContraseña());
        return clienteValidado.isPresent()
                ? ResponseEntity.ok(clienteValidado.get())
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }
}
