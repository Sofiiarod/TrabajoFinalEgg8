package com.scire.controladores;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scire.entidades.Usuario;
import com.scire.errores.ErrorException;
import com.scire.servicios.UsuarioServicio;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

	@Autowired
	private UsuarioServicio usuarioServicio;

	@GetMapping("/registro")
	public String registro() {
		return "registro-usuario.html";
	}

	@PostMapping("/registrar")
	public String registrar(ModelMap model, @RequestParam String nombre, @RequestParam String apellido,
			@RequestParam String email, @RequestParam String clave, @RequestParam String clave2) throws ErrorException {
		try {
			usuarioServicio.guardar(nombre, apellido, email, clave, clave2);
			model.put("exito", "Se ha registrado con éxito");
		} catch (Exception e) {
			model.put("error", e.getMessage());
			return "registro-usuario.html";
		}
//		System.out.println("Nombre: " + nombre);
//		System.out.println("Apellido: " + apellido);
//		System.out.println("Email: " + email);
//		System.out.println("Clave: " + clave);
//		System.out.println("Clave2: " + clave2);

		return "registro-usuario.html";

	}

	/**
	 * 
	 * @param session captura el usuario logueado
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_USER')") // El Usuario puede editar el perfil si solo si esta registrado
	@GetMapping("/editar-perfil")
	public String editarPerfil(HttpSession session, @RequestParam String id, ModelMap model) {
		Usuario logueado = (Usuario) session.getAttribute("usuariosession"); // aca va a obtener y usar un usuario
																				// logueado usa logueado como variable
		if (logueado == null || !logueado.getId().equals(id)) {// si logueado es null significa que en esa session no
																// hay ningun usuario
			return "index.html"; // || y si el usuario logueado no es igual al usuario que quiere
													// modificar lo mando a la csm

		}
		try {
			Usuario usuario = usuarioServicio.buscarPorId(id);
			model.addAttribute("perfil", usuario);
		} catch (ErrorException e) {
			model.addAttribute("error", e.getMessage());
		}
		return "perfil.html";
	}

	@PreAuthorize("hasAnyRole('ROLE_USER')") // El Usuario puede editar el perfil si solo si esta registrado
	@PostMapping("/actualizar-perfil")
	public String actualizar(ModelMap model, HttpSession session, @RequestParam String id, @RequestParam String nombre,
			@RequestParam String apellido,@RequestParam String clave,
			@RequestParam String clave2) {
		
		Usuario logueado = (Usuario) session.getAttribute("usuariosession");
		if (logueado == null || !logueado.getId().equals(id)) {
			return "index.html";
			}
		
		try {
			Usuario usuario = usuarioServicio.buscarPorId(id);
               usuarioServicio.modificar(id, nombre, apellido, clave, clave2);
               session.setAttribute("usuariosession", usuario); // es para usar el usuario logueado en thymeleaf 
               return "inicio.html";
		} catch (ErrorException e) {
			model.addAttribute("error", e.getMessage());
			return "perfil.html";
		}


	}
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@GetMapping("/recuperacion")
	public String recuperacion() {
		return "recuperar.html";
	}
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PostMapping("/recuperar")
	public String recuperar(ModelMap model,@RequestParam String email) throws ErrorException {
		try {
			String retorno = checkearEmail(email);
			if (retorno.equals("usuario")) {
				usuarioServicio.recuperarContrasenia(email);
			}
		} catch (ErrorException e) {
			model.addAttribute("error", e.getMessage());
			return "recuperar.html";
		}
		
		 model.put("exito", "Se ha enviado tu nueva contraseña a tu mail. Luego podrás cambiarla en tu perfil.");
	        return "login.html";
		
	}
	private String checkearEmail(String mail) throws ErrorException {
		 Usuario u = usuarioServicio.buscarPorEmail(mail);
		 if (u == null) {
			 throw new ErrorException("No hay nadie con ese mail.");
		}
		 return "usuario";
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/listar")
	public String listaUsuarios(ModelMap model) {
		List<Usuario> usuarios = usuarioServicio.mostrarTodos();
		model.addAttribute("usuarios", usuarios);
		return "list-usuarios.html";
	}
	
	
}

