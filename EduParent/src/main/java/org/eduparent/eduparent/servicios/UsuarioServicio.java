package org.eduparent.eduparent.servicios;

import org.eduparent.eduparent.entidades.Usuario;
import org.eduparent.eduparent.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioServicio() {
    }

    public Usuario validarUsuario(String nombre, String password) {
        return this.usuarioRepository.findByNombreAndPassword(nombre, password);
    }
}
