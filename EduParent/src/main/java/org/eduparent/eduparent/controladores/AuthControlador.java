package org.eduparent.eduparent.controladores;

import java.util.Optional;
import org.eduparent.eduparent.entidades.Cliente;
import org.eduparent.eduparent.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:5173")
public class AuthControlador {

    @Autowired
    private ClienteServicio clienteServicio;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Cliente cliente) {
        Optional<Cliente> clienteValidado = clienteServicio.validarClientePorDni(cliente.getDni(), cliente.getContraseña());
        return clienteValidado.isPresent() ?
                ResponseEntity.ok(clienteValidado.get()) :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }
}
