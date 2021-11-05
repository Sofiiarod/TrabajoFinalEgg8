package com.scire.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scire.entidades.Curso;
import com.scire.entidades.Profesor;
import com.scire.errores.ErrorException;
import com.scire.servicios.CursoServicio;
import com.scire.servicios.ProfesorServicio;



@Controller
@RequestMapping("/")
public class MainControlador {
	
	@Autowired
	CursoServicio cursoServicio;
	@Autowired
	ProfesorServicio profesorServicio;
	
	@GetMapping("/")
	public String index(ModelMap modelo){
		List<Curso> cursos = cursoServicio.listarTodos();
		Curso curso1 = cursos.get(0);
		Curso curso2 = cursos.get(1);
		Curso curso3 = cursos.get(2);
		Curso curso4 = cursos.get(3);
		modelo.addAttribute("curso1", curso1);
		modelo.addAttribute("curso2", curso2);
		modelo.addAttribute("curso3", curso3);
		modelo.addAttribute("curso4", curso4);
		
			
	return "../template/index.html";
	
	}
	
	}
	
	
		
	
	
	
	
//	@GetMapping()
//	public String index() {
//		return "index.html"; //tenemos que poner el html al cual queremos ir 
//	}
//	

	

	


