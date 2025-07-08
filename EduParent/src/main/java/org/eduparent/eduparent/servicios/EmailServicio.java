package org.eduparent.eduparent.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import org.springframework.scheduling.annotation.Async;

@Service
@RequiredArgsConstructor
public class EmailServicio {

    private final JavaMailSender mailSender;

    @Async  // ⬅️ Hace que este método se ejecute en otro hilo
    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom("renato.cortez@tecsup.edu.pe");
            mensaje.setTo(destinatario);
            mensaje.setSubject(asunto);
            mensaje.setText(cuerpo);

            mailSender.send(mensaje);

            System.out.println("Correo enviado exitosamente a: " + destinatario);
        } catch (Exception e) {
            System.out.println("Error al enviar correo: " + e.getMessage());
        }
    }
}
