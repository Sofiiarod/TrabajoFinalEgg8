package com.scire.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {
	
	
	
	
	@GetMapping("/menu")
	public String menu() {
		return "../template/menu.html";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "../template/admin.html";
	}
	
	@GetMapping("/user")
	public String user() {
		return "../template/user.html";
	}
	
}
