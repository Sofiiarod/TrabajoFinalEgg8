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
	
	/**
	 * 
	 * @param modelo
	 * @return String de index con plantillas 
	 * de las imagenes de los videos cargados en base de datos
	 */
	@GetMapping("/")
	public String index(ModelMap modelo){
		List<Curso> cursos = cursoServicio.listarTodos();	
		try {
			Curso curso1 = cursos.get(0);
			Curso curso2 = cursos.get(1);
			Curso curso3 = cursos.get(2);
			Curso curso4 = cursos.get(3);
			
			modelo.addAttribute("curso1", curso1);
			modelo.addAttribute("curso2", curso2);
			modelo.addAttribute("curso3", curso3);
			modelo.addAttribute("curso4", curso4);
			
			modelo.addAttribute("plantilla1", cursoServicio.urlImagen(curso1.getId()));
			modelo.addAttribute("plantilla2", cursoServicio.urlImagen(curso2.getId()));
			modelo.addAttribute("plantilla3", cursoServicio.urlImagen(curso3.getId()));
			modelo.addAttribute("plantilla4", cursoServicio.urlImagen(curso4.getId()));
			
			List<Profesor> profesor = profesorServicio.mostrarTodos();
			Profesor profesor1 = profesor.get(0);
			Profesor profesor2 = profesor.get(1);
			Profesor profesor3 = profesor.get(2);
			
			modelo.addAttribute("profesor1", profesor1);
			modelo.addAttribute("profesor2", profesor2);
			modelo.addAttribute("profesor3", profesor3);
			
		}catch(ErrorException e) {
			e.getMessage();
		}

		

	return "../template/index.html";
	
	}
	
	}
	
	
		
	
	
	
	
//	@GetMapping()
//	public String index() {
//		return "index.html"; //tenemos que poner el html al cual queremos ir 
//	}
//	

	

	


