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
import com.scire.errores.ErrorException;
import com.scire.repositorios.CategoriaRepositorio;
import com.scire.servicios.CategoriaServicio;


@Controller
@RequestMapping("/categorias")
public class CategoriaControlador {
	

	@Autowired
	private CategoriaServicio categoriaServicio;
	
	
	@GetMapping("/lista")
	public String categorias(ModelMap modeloDeCategorias) throws ErrorException {
		List<Categoria> misCategorias = categoriaServicio.mostrarTodos();
		modeloDeCategorias.addAttribute("misCategorias", misCategorias);
		return "lista-categorias";
	}
//	@GetMapping("/listarporCategorias")
//	public String listarporCategoria() {
//		
//	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") String id, ModelMap model) throws ErrorException {
		Categoria categoria = categoriaServicio.buscarPorId(id);
		model.addAttribute("categoria", categoria);
		return "modificar-formulario-categoria";
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/editar/guardar")
	public String guardar(String id, String nombre, ModelMap model) throws ErrorException {
		categoriaServicio.modificar(id, nombre);
		model.put("exito", "Se ha modificado con exito");
		return "redirect:/categorias/lista";
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/registro")
	public String registro() {
		
		return "registro-categoria";
		
	}
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/registrar")
	public String registrar(ModelMap model, @RequestParam String nombre) throws ErrorException {
		String res = "redirect:/categorias/lista";
		
		try {
			
			categoriaServicio.guardar(nombre);
			model.put("exito", "Se ha registrado con éxito");
			
			
		} catch (Exception e) {
			model.put("error", e.getMessage());
			return "registro-categoria";
		}
		return res;
		}
}

	
	
	
	

