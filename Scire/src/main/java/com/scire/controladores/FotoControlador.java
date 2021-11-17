package com.scire.controladores;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scire.entidades.Usuario;
import com.scire.errores.ErrorException;
import com.scire.servicios.UsuarioServicio;

@Controller
@RequestMapping("/foto")
public class FotoControlador {
	@Autowired
	private UsuarioServicio usuarioServicio;
	@GetMapping("/usuario")
	public ResponseEntity<byte[]> fotoUsuario(@RequestParam String id) throws ErrorException {
		
		try {
			Usuario usuario = usuarioServicio.buscarPorId(id);
			if(usuario.getFoto() == null ) {
				throw new ErrorException("El usuario no tiene una foto");
			}
			byte [] foto = usuario.getFoto().getContenido();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG);
			return new ResponseEntity<>(foto, headers, HttpStatus.OK);
			
		} catch (ErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
}
