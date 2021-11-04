package com.scire.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class NotificacionServicio {
@Autowired(required = true)
 private JavaMailSender sender;
//@Autowired
//private UsuarioServicio usuarioserv;


@Async
public void enviar(String cuerpo, String titulo, String email) {
    SimpleMailMessage mensaje = new SimpleMailMessage();

    mensaje.setTo(email);
    mensaje.setFrom("scireedu@gmail.com");
    mensaje.setSubject(titulo);
    mensaje.setText(cuerpo);

    sender.send(mensaje);
}
@Async
<<<<<<< HEAD
public void enviarModificarContraseña(String cuerpo, String titulo, String email, String contraseña) {
=======
 public void enviarModificarContraseña(String cuerpo, String titulo, String mail, String contraseña) {
>>>>>>> e4d82cf2fd46126a4ad40bd6ab54fc47fefb59bb
    SimpleMailMessage mensaje = new SimpleMailMessage();
    String mensajeAnterior = "";

    mensajeAnterior = " Su nueva Contraseña es: " + contraseña;

    mensaje.setFrom("scireedu@gmail.com");
    mensaje.setTo(email);
    mensaje.setSubject(titulo);
    mensaje.setText(mensajeAnterior.concat(cuerpo));

    sender.send(mensaje);
}





}



