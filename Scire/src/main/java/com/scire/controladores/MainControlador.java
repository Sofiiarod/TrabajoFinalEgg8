package com.scire.controladores;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.scire.entidades.Curso;
import com.scire.entidades.Profesor;

//import com.scire.entidades.Usuario;
import com.scire.errores.ErrorException;
//import com.scire.repositorios.UsuarioRepositorio;
import com.scire.servicios.CursoServicio;
import com.scire.servicios.ProfesorServicio;
//import com.scire.servicios.UsuarioServicio;




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



	return "index.html";
	
	}


/**
 * 
 * @param error //Si el usuario ingresa algo incorrecto ingresa al model y imprime el msj a travez de "error" en thymeleaf
 *  y lo controlamos como parametro de http
 * @param logout // llega como parametro en el http y lo usamos en el inicio para salir de la cuenta
 * @param model
 * @return -> Dea
 */
	@GetMapping("/login")
	public String login(HttpSession session,@RequestParam(required = false)String error ,@RequestParam(required = false)String logout,ModelMap model) {
		if (error != null) {
			model.put("error","Nombre de Usuario o clave incorrectos");
		}else {
			model.addAttribute("error",error);
		}
		if (logout != null) { 
			model.addAttribute("logout","Ha salido correctamente");
		}
		if (session.getAttribute("usuariosession") != null) {
			return "inicio.html";
		}else {
			return "login.html";
		}
	}

	
//	@PreAuthorize("hasAnyRole('ROLE_USER')") //Autoriza al usuario entrar si solo si esta autenticado
//	@GetMapping("/loginsuccess") // es lo mismo que inicio en configSeguridad podemos poner asi  como /inicio -> FIUMBA
//	public String loginresolver() {
//				
//		return "redirect:/cursos";
//	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@RequestMapping("/cursos")
	public String loginUsuario() {
		return "redirect:/cursos";
	}
	
	/**
	 * TODO arreglar la redireccion del admin 
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping("/admin")
	public String loginAdmin() {
		return "redirect:/admin/inicio";
	}

	
	}
	
	
	
