package com.scire.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scire.entidades.Categoria;
import com.scire.entidades.Curso;
import com.scire.entidades.Profesor;
import com.scire.errores.ErrorException;
import com.scire.servicios.CategoriaServicio;
import com.scire.servicios.CursoServicio;
import com.scire.servicios.ProfesorServicio;



@Controller
@RequestMapping("/cursos")
public class CursoControlador {

	@Autowired
	CursoServicio cursoServicio;
	@Autowired
	CategoriaServicio categoriaServicio;
	@Autowired
	ProfesorServicio profesorServicio;
	
	

	
	@GetMapping()
	public String cursosActivos(ModelMap modelo){
		try {
			List<Curso> cursosActivos = cursoServicio.buscarCursosPorEstado(true);
			modelo.addAttribute("cursos", cursosActivos);
			List<Categoria> listaCategoriasActivas = categoriaServicio.mostrarTodos();
			modelo.addAttribute("categorias", listaCategoriasActivas);
			List<Profesor> listaProfesoresActivos = profesorServicio.mostrarTodos();
			modelo.addAttribute("profesores", listaProfesoresActivos);
			
			
		} catch (ErrorException e) {
				e.getMessage();
		}
		
		return "cursos/index-menu-vertical.html";
	}
	
	
	
	
	@GetMapping("/categorias")
	public String categoriasActivas(@RequestParam String idCategoria, ModelMap modelo){

		try {
			
			List<Curso> cursosPorCategoria = cursoServicio.buscarCursosActivosPorCategoria(idCategoria);
			modelo.addAttribute("cursos", cursosPorCategoria);
			List<Categoria> listaCategoriasActivas = categoriaServicio.mostrarTodos();
			modelo.addAttribute("categorias", listaCategoriasActivas);
			List<Profesor> listaProfesoresActivos = profesorServicio.mostrarTodos();
			modelo.addAttribute("profesores", listaProfesoresActivos);
				
		
		} catch (ErrorException e) {
				e.getMessage();
		}
		
		return "cursos/index-menu-vertical.html";
	}
	
	@GetMapping("/profesores")
	public String profesoresActivos(@RequestParam String idProfesor, ModelMap modelo){

		try {
			
			List<Curso> cursosPorProfesor = cursoServicio.buscarCursosActivosPorProfesor(idProfesor);
			modelo.addAttribute("cursos", cursosPorProfesor);
			List<Categoria> listaCategoriasActivas = categoriaServicio.mostrarTodos();
			modelo.addAttribute("categorias", listaCategoriasActivas);
			List<Profesor> listaProfesoresActivos = profesorServicio.mostrarTodos();
			modelo.addAttribute("profesores", listaProfesoresActivos);
				
		
		} catch (ErrorException e) {
				e.getMessage();
		}
		
		return "cursos/index-menu-vertical.html";
	}
	
	
	
	
	
	
	
	
	
}
