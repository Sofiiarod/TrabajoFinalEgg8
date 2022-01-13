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
		mensaje.setFrom("scirecomunidad@gmail.com");
		mensaje.setSubject(titulo);
		mensaje.setText(cuerpo);


    sender.send(mensaje);
}
@Async
public void enviarModificarContraseña(String cuerpo, String titulo, String email, String contraseña) {

    SimpleMailMessage mensaje = new SimpleMailMessage();
    String mensajeAnterior = "";

    mensajeAnterior = "Hola de nuevo,"+"\n"
    		        + "Ingresa con esta nueva clave: " + contraseña + "\n"+"\n"
    		        + "Saludos"+"\n"
    		        + "Comunidad Scire"+ "\n";

    mensaje.setFrom("scireedu@gmail.com");
    mensaje.setTo(email);
    mensaje.setSubject(titulo);
    mensaje.setText(mensajeAnterior.concat(cuerpo));

    sender.send(mensaje);
}
}
