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

import com.scire.entidades.Profesor;
import com.scire.errores.ErrorException;
import com.scire.servicios.CursoServicio;
import com.scire.servicios.ProfesorServicio;


@Controller
@RequestMapping("/profesores")
public class ProfesorControlador {
	
	
	
	@Autowired
	private ProfesorServicio profesorServicio;
	@Autowired
	private CursoServicio cursoServicio;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/lista")
	public String profesores(ModelMap modeloDeProfesores) throws ErrorException {
		List<Profesor> misProfesores = profesorServicio.mostrarTodos();
		modeloDeProfesores.addAttribute("misProfesores", misProfesores);
		return "lista-profesores";
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/registro")
	public String registro() {
		
		return "registro-profesor";
		
	}

	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/registrar")
	public String registrar(ModelMap model, @RequestParam String nombre) throws ErrorException {
		String res = "redirect:/profesores/lista";
		
		try {
			
			profesorServicio.guardar(nombre);
			model.put("exito", "Se ha registrado con éxito");
			
		} catch (Exception e) {
			model.put("error", e.getMessage());
			return "registro-profesor";
		}
		return res;
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") String id, ModelMap model) throws ErrorException {
		Profesor profesor = profesorServicio.buscarPorId(id);
		model.addAttribute("profesor", profesor);
		return "modificar-formulario-profesor";
	}
	@PostMapping("/editar/guardar")
	public String guardar(String id, String nombre, ModelMap model) throws ErrorException {
		try {
			profesorServicio.modificar(id, nombre);
			model.put("exito", "Se ha modificado con exito");
		} catch (Exception e) {
			model.put("error", e.getMessage());
			return "modificar-formulario-profesor";
		}
		
		return "redirect:/profesores/lista";
		
	}
	@GetMapping("/eliminar/{id}")
	public String delete(@PathVariable("id") String id, ModelMap model) throws ErrorException {
		profesorServicio.eliminar(id);
		return "redirect:/profesores/lista";
	}
	@GetMapping("/daralta/{id}")
	public String darAlta(@PathVariable("id") String id, ModelMap model) throws ErrorException {
		try {
			profesorServicio.alta(id);
		} catch (Exception e ) {
			throw new ErrorException ("No se ha encontrado el profesor solicitado");
		}
		
		return "redirect:/profesores/lista";
	}
//	@GetMapping("/darbaja/{id}")
//	public String darBaja(@PathVariable("id") String id, ModelMap model) throws ErrorException {
//		try {
//			profesorServicio.baja(id);
//		} catch ( Exception e ) {
//			throw new ErrorException ("No se ha encontrado el profesor solicitado");
//		}
//		
//		return "redirect:/profesores/lista";
//	}
	
			
	

}

