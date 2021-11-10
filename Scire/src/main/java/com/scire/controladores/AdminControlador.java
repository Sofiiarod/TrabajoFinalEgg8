package com.scire.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scire.entidades.Categoria;
import com.scire.entidades.Curso;
import com.scire.entidades.Profesor;
import com.scire.errores.ErrorException;
import com.scire.repositorios.CursoRepositorio;
import com.scire.servicios.CategoriaServicio;
import com.scire.servicios.CursoServicio;
import com.scire.servicios.ProfesorServicio;

/**
 * 
 * @author Ivan
 * El modelo que se usa en los getMapping de: 
 * inicio, lista-de-cursos, lista-de-categorias, lista-de-profesores, cargar-curso, cargar-categoria, cargar-profesor
 * Sirven para darle titulo a la barra de navegacion que esta en base-plantilla.html fragment.
 */
@Controller
@RequestMapping("/admin")

public class AdminControlador {
	
	@Autowired
	CursoServicio cursoServicio;
	
	@Autowired
	ProfesorServicio profesorServicio;
	
	@Autowired
	CategoriaServicio categoriaServicio;
	
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/inicio")
	public String inicio(ModelMap modelo) {
		try {
			modelo.addAttribute("titulo", "Inicio");
			return "plantillas-admin/index-admin";
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
		
	}
	
	@GetMapping("/lista-de-cursos")
	public String listaDeCursos(ModelMap modelo) {

			List<Curso> cursos = cursoServicio.listarTodos();
			
			modelo.addAttribute("titulo", "Lista de cursos");
			modelo.addAttribute("cursos", cursos);
			return "plantillas-admin/lista-cursos";
	
		
	}
	
	
	/**
	 * 
	 * @param modelo
	 * @return
	 */
	@GetMapping("/lista-de-categorias")
	public String listaDeCategorias(ModelMap modelo) {
		try {
			List<Categoria> categorias = categoriaServicio.listarTodos();
			
			modelo.addAttribute("categorias", categorias);
			
			modelo.addAttribute("titulo", "Lista de categorías");
			
			return "plantillas-admin/lista-categoria";
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}

	}
	
	@GetMapping("/lista-de-profesores")
	public String listaDeProfesores(ModelMap modelo) {
		try {
			List <Profesor> profesores = profesorServicio.mostrarTodos();
			
			modelo.addAttribute("titulo", "Lista de profesores/as");
			modelo.addAttribute("profesores", profesores);
			
			return "plantillas-admin/lista-profesor";
		} catch (ErrorException e) {
			
			return "plantillas-admin/error-admin";
		}

	}
	
	@GetMapping("/cargar-curso")
	public String cargarCurso(ModelMap modelo) {
		
		try {
			
			modelo.addAttribute("profesores",profesorServicio.mostrarTodos());
			modelo.addAttribute("categorias",categoriaServicio.listarTodos());
			modelo.addAttribute("titulo", "Nuevo curso");
			
		} catch (Exception e) {
			// TODO: handle exception
			return "plantillas-admin/cargar-curso";
		}
		
		return "plantillas-admin/cargar-curso";
	}
	
	@PostMapping("/agregar-curso")
	public String agregarCurso(ModelMap modelo,
			@RequestParam("nombreDelCurso") String nombreDelCurso,
			@RequestParam("descripcion") String descripcion,
			@RequestParam("idDeYoutube") String idDeYoutube,
			@RequestParam("idCategoria") String idCategoria,
			@RequestParam("idProfesor") String idProfesor) {
		
		
		
		try {
			
			
			Categoria categoria = categoriaServicio.buscarPorId(idCategoria);
			Profesor profesor = profesorServicio.buscarPorId(idProfesor);
			
			
			
			cursoServicio.guardar(nombreDelCurso, descripcion, idDeYoutube, categoria, profesor);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return "redirect:/admin/lista-de-cursos";
	}
	
	@GetMapping("/cargar-categoria")
	public String cargarCategoria(ModelMap modelo) {
		try {
			modelo.addAttribute("titulo", "Nueva categoria");
			return "/plantillas-admin/cargar-categoria";
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
		

	}
	/**TODO 
	 * HACER MANEJO DE ERRORES 
	 * @param nombreDeCategoria
	 * @return redireccion a admin/lista-de-cursos
	 * @throws ErrorException
	 */
	@PostMapping("/agregar-categoria")
	public String guardarCategoria(@RequestParam("nombreDeCategoria") String nombreDeCategoria) throws ErrorException {
		try {
			categoriaServicio.guardar(nombreDeCategoria);
			return "redirect:/admin/lista-de-categorias";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/admin/lista-de-categorias";
	}
	
	@GetMapping("/cargar-profesor")
	public String cargarProfesor(ModelMap modelo) {
		
		try {
			modelo.addAttribute("titulo", "Nuevo profesor/a");
			return "plantillas-admin/cargar-profesor";
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
		

	}
	
	@PostMapping("/agregar-profesor")
	public String agregarProfesor(@RequestParam("nombreDelProfesor") String nombreDelProfesor) {
		try {
			profesorServicio.guardar(nombreDelProfesor);
			return "redirect:/admin/lista-de-profesores";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/admin/lista-de-profesores";
	}
	
	
	@GetMapping("/modificar-estado/{id}")
	public String modificarEstado(@PathVariable("id") String idCurso) {
		
		
		try {
			Curso curso = cursoServicio.encontrarPorID(idCurso);
			if(curso.getEstado()) {
				cursoServicio.baja(curso.getId());
			}else {
				cursoServicio.alta(curso.getId());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return "redirect:/admin/lista-de-cursos";
	}
	
	@GetMapping("/editar-curso/{id}")
	public String modificarCurso(ModelMap modelo,@PathVariable("id") String idCurso) {
		
		try {
			Curso curso = cursoServicio.encontrarPorID(idCurso);
			
			modelo.addAttribute("profesores",profesorServicio.mostrarTodos());
			modelo.addAttribute("categorias",categoriaServicio.listarTodos());
			
			modelo.addAttribute("titulo",curso.getNombre());
			modelo.addAttribute("curso", curso);
			
		//	modelo.addAttribute("exito", "Se ha editado con exito");
			
			return "plantillas-admin/editar-curso";
		} catch (Exception e) {
			
			//modelo.addAttribute("error", e.getMessage());
		}
		
		return "plantillas-admin/editar-curso";
	}
	
	@PostMapping("/edicion-curso")
	public String editarCurso(ModelMap modelo,
			@RequestParam("id") String id, 
			@RequestParam("nombreDelCurso") String nombreDelCurso,
			@RequestParam("descripcion") String descripcion,
			@RequestParam("idDeYoutube") String idDeYoutube,
			@RequestParam("idCategoria") String idCategoria,
			@RequestParam("idProfesor") String idProfesor) {
		
		try {
			
			Categoria categoria = categoriaServicio.buscarPorId(idCategoria);
			Profesor profesor = profesorServicio.buscarPorId(idProfesor);
			
			cursoServicio.modificar(id, nombreDelCurso, descripcion, idDeYoutube, categoria, profesor);
			
			//modelo.addAttribute("exito", "Se ha editado con exito");
			
			//return "redirect:/admin/lista-de-cursos";
		} catch (Exception e) {
		//	modelo.addAttribute("error", e.getMessage());
		}
		
		return "redirect:/admin/lista-de-cursos";
	}
	
	@GetMapping("/editar-categoria/{id}")
	public String modificarCategoria(ModelMap modelo,@PathVariable("id") String idCurso) {
		
		try {
			Categoria categoria = categoriaServicio.buscarPorId(idCurso);
			
			modelo.addAttribute("titulo",categoria.getNombre());
			modelo.addAttribute("categoria", categoria);
			
			return "plantillas-admin/editar-categoria";
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "plantillas-admin/editar-categoria";
	}
	
	@PostMapping("/edicion-categoria")
	public String editarCategoria(
			@RequestParam("id") String id, 
			@RequestParam("nombreDeCategoria") String nombreDeCategoria) {
		
		try {
			
			// Categoria categoria = categoriaServicio.buscarPorId(id);
			
			categoriaServicio.modificar(id, nombreDeCategoria);
			
			
			return "redirect:/admin/lista-de-categorias";
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/admin/lista-de-categorias";
	}
	
	@GetMapping("/editar-profesor/{id}")
	public String editarProfesor(ModelMap modelo, @PathVariable("id") String idProfesor) {
		
		try {
			
			Profesor profesor = profesorServicio.buscarPorId(idProfesor);
			
			modelo.addAttribute("titulo",profesor.getNombre());
			modelo.addAttribute("profesor", profesor);
			
			return "plantillas-admin/editar-profesor";
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "plantillas-admin/editar-profesor";
	}
	
	@GetMapping("/modificar-estado-profesor/{id}")
	public String modificarEstadoProfesor(@PathVariable("id") String id) {
		
		try {
				profesorServicio.alta(id);
	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return "redirect:/admin/lista-de-profesores";
	}
	
	@PostMapping("/edicion-profesor")
	public String editarProfesor(
			@RequestParam("id") String id, 
			@RequestParam("nombreDeProfesor") String nombreDeProfesor) {
		
		try {
			profesorServicio.modificar(id, nombreDeProfesor);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/admin/lista-de-profesores";
	}
	
	
}
