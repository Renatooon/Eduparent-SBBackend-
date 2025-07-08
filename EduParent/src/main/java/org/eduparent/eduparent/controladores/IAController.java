package org.eduparent.eduparent.controladores;

import org.eduparent.eduparent.dto.ConsultaIARequest;
import org.eduparent.eduparent.servicios.IAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/ia")
@CrossOrigin(origins = "*")
public class IAController {

    @Autowired
    private IAService iaService;

    @PostMapping("/consultar")
    public ResponseEntity<String> consultarIA(@RequestBody ConsultaIARequest request) {
        String respuesta = iaService.procesarPreguntaDelPadre(request.getDni(), request.getPregunta());
        return ResponseEntity.ok(respuesta);
    }
}