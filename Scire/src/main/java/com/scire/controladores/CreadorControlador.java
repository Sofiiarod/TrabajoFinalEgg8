package com.scire.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scire.entidades.Profesor;
import com.scire.errores.ErrorException;
import com.scire.servicios.ProfesorServicio;


@Controller
@RequestMapping("/")
public class CreadorControlador {
	
	
	
	@Autowired
	private ProfesorServicio profesorServicio;
	
	
	
	@GetMapping("/profesores")
	public String profesores(ModelMap modeloDeProfesores) throws ErrorException {
		List<Profesor> misProfesores = profesorServicio.mostrarTodos();
		modeloDeProfesores.addAttribute("misProfesores", misProfesores);
		return "../template/lista-profesores.html";
	}
	
	
	
	@GetMapping("/profesores/create")
	public String registro() {
		
		return "../template/formulario-profesor.html";
		
	}
	
	
	
	@PostMapping("/profesores/create")
	public String guardar(ModelMap model, @RequestParam String nombre) throws ErrorException {
		String res = "redirect:/profesores";
		
		
		try {
			
			profesorServicio.guardar(nombre);
			
		} catch (Exception e) {
			model.put("Error", e.getMessage());
			return "../template/formulario-profesor.html";
		}
		return res;
	}
	

}
