package com.scire.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scire.errores.ErrorException;
import com.scire.servicios.UsuarioServicio;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

	@Autowired
	private UsuarioServicio usuarioServicio;


	@GetMapping("/menu")
	public String menu() {
		return "menu.html";
	}

	@GetMapping("/admin")
	public String admin() {
		return "admin.html";
	}

	@GetMapping("/user")
	public String user() {
		return "user.html";
	}

	@GetMapping("/registro")
	public String registro() {
		return "registro-usuario.html";
	}

	@PostMapping("/registrar")
	public String registrar(ModelMap model, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email,@RequestParam String clave,@RequestParam String clave2) throws ErrorException{
		try {
			usuarioServicio.guardar(nombre, apellido, email, clave, clave2);
			model.put("exito", "Se ha registrado con éxito");
		} catch (Exception e) {
			model.put("error", e.getMessage());
			return "registro-usuario.html";
		}
//		System.out.println("Nombre: " + nombre);
//		System.out.println("Apellido: " + apellido);
//		System.out.println("Email: " + email);
//		System.out.println("Clave: " + clave);
//		System.out.println("Clave2: " + clave2);
		
		return "../template/registro-usuario.html";

	}

}
