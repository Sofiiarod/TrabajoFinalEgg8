package com.scire.controladores;

import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.scire.entidades.Usuario;
import com.scire.errores.ErrorException;
import com.scire.servicios.CategoriaServicio;
import com.scire.servicios.CursoServicio;
import com.scire.servicios.ProfesorServicio;
import com.scire.servicios.UsuarioServicio;


@Controller
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
@RequestMapping("/cursos")
public class CursoControlador {

	@Autowired
	CursoServicio cursoServicio;
	@Autowired
	CategoriaServicio categoriaServicio;
	@Autowired
	ProfesorServicio profesorServicio;
	@Autowired
	UsuarioServicio usuarioServicio;
	
	

	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@GetMapping()
	public String cursosActivos(ModelMap modelo) throws ErrorException {
		try {
			List<Curso> cursosActivos = cursoServicio.buscarCursosPorEstado(true);
			modelo.addAttribute("cursos", cursosActivos);

			List<Categoria> listaCategoriasActivas = categoriaServicio.listarTodos();

//			List<Categoria> listaCategoriasActivas = categoriaServicio.mostrarTodos();
			modelo.addAttribute("categorias", listaCategoriasActivas);
			List<Profesor> listaProfesoresActivos = profesorServicio.mostrarTodos();
			modelo.addAttribute("profesores", listaProfesoresActivos);
			
		} catch (ErrorException e) {
				e.getMessage();
		}
		
		return "/cursos/index-menu-vertical.html";
	}



	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@GetMapping("/ver")
	public String vistaCurso(ModelMap model,@RequestParam String idCurso, HttpSession session) throws ErrorException {
    
		Curso curso = cursoServicio.encontrarPorID(idCurso);
		model.addAttribute("curso",curso);
		Usuario logueado = (Usuario) session.getAttribute("usuariosession");
		Boolean valor = cursoServicio.evaluaInscripcion(logueado.getId(), idCurso);
		model.addAttribute("inscripto", valor);
		return "cursos/vista-curso.html";

	}
	
	
	

	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@GetMapping("/categorias")
	public String categoriasActivas(@RequestParam String idCategoria, ModelMap modelo){

		try {
			
			List<Curso> cursosPorCategoria = cursoServicio.buscarCursosActivosPorCategoria(idCategoria);
			modelo.addAttribute("cursos", cursosPorCategoria);
			List<Categoria> listaCategoriasActivas = categoriaServicio.listarTodos();
			modelo.addAttribute("categorias", listaCategoriasActivas);
			List<Profesor> listaProfesoresActivos = profesorServicio.mostrarTodos();
			modelo.addAttribute("profesores", listaProfesoresActivos);
				
		
		} catch (ErrorException e) {
				e.getMessage();
		}
		
		return "cursos/index-menu-vertical.html";
	}

	@GetMapping("/registro")
	public String registro() {
		return "registro-curso.html";
	}

	@PostMapping("/registrar")
	public String registrar(ModelMap model,@RequestParam String nombre,@RequestParam String descripcion,@RequestParam String url,@RequestParam Categoria categoriaID,@RequestParam Profesor profesorID) throws ErrorException{
		try {
			
			cursoServicio.guardar(nombre, descripcion, url, categoriaID, profesorID);
			model.put("exito", "Se ha registrado con éxito");
			
		} catch (Exception e) {
			
			model.put("error", e.getMessage());
			return "registro-curso.html";
			
		}
		
		return "index.html";

	}


	
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@GetMapping("/profesores")
	public String profesoresActivos(@RequestParam String idProfesor, ModelMap modelo){

		try {
			
			List<Curso> cursosPorProfesor = cursoServicio.buscarCursosActivosPorProfesor(idProfesor);
			modelo.addAttribute("cursos", cursosPorProfesor);
			List<Categoria> listaCategoriasActivas = categoriaServicio.listarTodos();
			modelo.addAttribute("categorias", listaCategoriasActivas);
			List<Profesor> listaProfesoresActivos = profesorServicio.mostrarTodos();
			modelo.addAttribute("profesores", listaProfesoresActivos);
				
		
		} catch (ErrorException e) {
				e.getMessage();
		}
		
		return "cursos/index-menu-vertical.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@GetMapping("/misCursos")
	public String misCursos(@RequestParam String idUsuario, ModelMap modelo){

		try {
						
			List<Curso> cursosDeUsuario = cursoServicio.buscarCursosActivosPorUsuario(idUsuario);
			modelo.addAttribute("cursos", cursosDeUsuario);
			List<Categoria> listaCategoriasActivas = categoriaServicio.listarTodos();
			modelo.addAttribute("categorias", listaCategoriasActivas);
			List<Profesor> listaProfesoresActivos = profesorServicio.mostrarTodos();
			modelo.addAttribute("profesores", listaProfesoresActivos);
				
		
		} catch (ErrorException e) {
				e.getMessage();
		}
		
		return "cursos/index-menu-vertical.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@PostMapping("/agregaralista/{idCurso}")
	public String AgregarALista(@RequestParam("idUsuario") String idUsuario, @PathVariable("idCurso") String idCurso, ModelMap modelo) throws ErrorException{

			
			cursoServicio.inscripcion(idUsuario, idCurso);	
			return "redirect:/cursos/ver?idCurso=".concat(idCurso);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@PostMapping("/sacardemilista/{idCurso}")
	public String SacardeLista(@RequestParam("idUsuario") String idUsuario, @PathVariable("idCurso") String idCurso, ModelMap modelo) throws ErrorException{

			
			cursoServicio.desinscripcion(idUsuario, idCurso);	
			return "redirect:/cursos/ver?idCurso=".concat(idCurso);
	}
}
