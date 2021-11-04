package com.scire.controladores;

import javax.servlet.http.HttpSession;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/")
public class MainControlador {
	
	
	
	@GetMapping("/")
	public String index() {
	return "../template/index.html";
	
	}
	@PreAuthorize("hasAnyRole('ROLE_USER')") //Autoriza al usuario entrar si solo si esta autenticado
	@GetMapping("/inicio")// Si el Usuario Se loguea correctamente deberia ir a inicio
	public String inicio() {
		return "../template/inicio.html";
	}
/**
 * 
 * @param error //Si el usuario ingresa algo incorrecto ingresa al model y imprime el msj a travez de "error" en thymeleaf
 *  y lo controlamos como parametro de http
 * @param logout // llega como parametro en el http y lo usamos en el inicio para salir de la cuenta
 * @param model
 * @return -> Dea
 */
	@GetMapping("/login")
	public String login(HttpSession session,@RequestParam(required = false)String error ,@RequestParam(required = false)String logout,ModelMap model) {
		if (error != null) {
			model.put("error","Nombre de Usuario o clave incorrectos");
		}else {
			model.addAttribute("error",error);
		}
		if (logout != null) { 
			model.addAttribute("logout","Ha salido correctamente");
		}
		if (session.getAttribute("usuariosession") != null) {
			return "../template/inicio";
		}else {
			return "../template/login.html";
		}
	}
	
	@GetMapping("/loginsuccess") // es lo mismo que inicio en configSeguridad podemos poner asi  como /inicio -> FIUMBA
	public String loginresolver() {
				
		return "redirect:/inicio";
	}
	
		
	
	
	
	


	
}
	


