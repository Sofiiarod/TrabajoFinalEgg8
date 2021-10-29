package com.scire.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificacionServicio {
@Autowired
 private JavaMailSender sender;
@Autowired
 private UsuarioServicio usuarioserv;

@Async
public void enviar(String cuerpo, String titulo, String mail) {
    SimpleMailMessage mensaje = new SimpleMailMessage();

    mensaje.setTo(mail);
    mensaje.setFrom("Scireedu@gmail.com");
    mensaje.setSubject(titulo);
    mensaje.setText(cuerpo);

    sender.send(mensaje);
}
@Async
public void enviarModificarContraseña(String cuerpo, String titulo, String mail, String contraseña) {
    SimpleMailMessage mensaje = new SimpleMailMessage();
    String mensajeAnterior = "";

    mensajeAnterior = " Su nueva Contraseña es: " + contraseña;

    mensaje.setFrom("Scireedu@gmail.com");
    mensaje.setTo(mail);
    mensaje.setSubject(titulo);
    mensaje.setText(mensajeAnterior.concat(cuerpo));

    sender.send(mensaje);
}




}
