package com.scire.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/cursos")
public class CursoControlador {

	
	@GetMapping("/programacion")
	public String programacion() {
		return "cursos/cursos.html";
	}
	
	
	
	
}
