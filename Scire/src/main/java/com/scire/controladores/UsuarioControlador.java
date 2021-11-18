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
import org.springframework.web.multipart.MultipartFile;

import com.scire.entidades.Usuario;
import com.scire.errores.ErrorException;
import com.scire.servicios.UsuarioServicio;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

	@Autowired
	private UsuarioServicio usuarioServicio;

	@GetMapping("/registrar")
	public String registrar() {
		return "registrarse.html";
	}

	@PostMapping("/registrar")
	public String registrar(ModelMap model, MultipartFile archivo, @RequestParam String nombre,
			@RequestParam String apellido, @RequestParam String email, @RequestParam String clave,
			@RequestParam String clave2) throws ErrorException {

			try {
				if (archivo == null) {
				usuarioServicio.guardar(null, nombre, apellido, email, clave, clave2);
				}
				usuarioServicio.guardar(archivo, nombre, apellido, email, clave, clave2);
				model.put("exito", "Se ha registrado con exito");
			} catch (ErrorException e) {

				model.put("error", e.getMessage());

				return "registrarse.html";

			}

			return "redirect:/login";

	}

	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@GetMapping("/perfil")
	public String ingresarPerfil(HttpSession session, @RequestParam String id, ModelMap model) {
		Usuario logueado = (Usuario) session.getAttribute("usuariosession");

		if (logueado == null || !logueado.getId().equals(id)) {

			return "index.html";
		}

		try {

			Usuario usuario = usuarioServicio.buscarPorId(id);

			model.addAttribute("perfil", usuario);

			return "/perfil/index-perfil";


		} catch (ErrorException e) {

			// model.addAttribute("error", e.getMessage());
			System.out.println("Error 79: " + e.getMessage());

		}
		return "/perfil/index-perfil";
	}

	/**
	 * 
	 * @param session captura el usuario logueado
	 * @return
	 */

	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )") // El Usuario puede editar el perfil si solo si esta
															// registrado
	@GetMapping("/editar-perfil")
	public String editarPerfil(HttpSession session, @RequestParam String id, ModelMap model) {
		Usuario logueado = (Usuario) session.getAttribute("usuariosession"); // aca va a obtener y usar un usuario logueado usa logueado como variable
		if (logueado == null || !logueado.getId().equals(id)) {// si logueado es null significa que en esa session no hay ningun usuario
			
			return "index.html"; // || y si el usuario logueado no es igual al usuario que quiere modificar lo mando a la csm
		}
		try {

			Usuario usuario = usuarioServicio.buscarPorId(id);

			model.addAttribute("perfil", usuario);

			return "/perfil/editar-perfil.html";

		} catch (ErrorException e) {

			System.out.println("Error 79: " + e.getMessage());

		}

		return "/perfil/editar-perfil.html";

	}


	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )") // El Usuario puede editar el perfil si solo si está registrado
	@PostMapping("/actualizar-perfil")
	public String actualizar(ModelMap model, HttpSession session, @RequestParam String id, @RequestParam String nombre,
			@RequestParam String apellido, String email) {

			String idRedirect = "";	

			Usuario logueado = (Usuario) session.getAttribute("usuariosession");

			if (logueado == null || !logueado.getId().equals(id)) {

			return "index.html";

			}

			try {

				Usuario usuario = usuarioServicio.buscarPorId(id);

				usuarioServicio.modificarDatos(id, nombre, apellido, email);

				session.setAttribute("usuariosession", usuario); // es para usar el usuario logueado en thymeleaf

				idRedirect = "?id=".concat(usuario.getId());

				return "redirect:/usuario/editar-perfil".concat(idRedirect);

			} catch (ErrorException e) {

				model.addAttribute("error", e.getMessage());

				System.out.println("Error: 103" + e.getMessage());

				return "/perfil/editar-perfil.html";
			
			}


	}


	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@GetMapping("/editar-foto")
	public String editarFoto(HttpSession session, @RequestParam String id, ModelMap model, MultipartFile archivo) {

		Usuario logueado = (Usuario) session.getAttribute("usuariosession");

		if (logueado == null || !logueado.getId().equals(id)) {

			return "redirect:/";

		}

		try {

			Usuario usuario = usuarioServicio.buscarPorId(id);

			model.addAttribute("perfil", usuario);

			return "/perfil/editar-foto.html";

		} catch (ErrorException e) {

			System.out.println("Error 121: " + e.getMessage());
		}

		return "/perfil/editar-foto.html";

	}


	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@PostMapping("/guardar-foto")
	public String guardarFoto(ModelMap model, HttpSession session, @RequestParam String id, MultipartFile archivo) {

		String idRedirect = "";

		Usuario logueado = (Usuario) session.getAttribute("usuariosession");

		if (logueado == null || !logueado.getId().equals(id)) {

			return "redirect:/";

		}

		try {

			Usuario usuario = usuarioServicio.buscarPorId(id);

			usuarioServicio.modificarFoto(id, archivo);

			session.setAttribute("usuariosession", usuario);

			idRedirect = "?id=".concat(usuario.getId());

			return "redirect:/usuario/editar-foto".concat(idRedirect);

		} catch (ErrorException e) {

			System.out.println("Error: 138" + e.getMessage());

			return "/perfil/editar-foto.html";

		}

	}


	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@GetMapping("/modificar-contraseña")
	public String modificarContraseña(ModelMap model, HttpSession session,@RequestParam String id) {
		
		Usuario logueado = (Usuario) session.getAttribute("usuariosession");
		if (logueado == null || !logueado.getId().equals(id)) {
			return "index.html";
			}

		
		try {
			   Usuario usuario = usuarioServicio.buscarPorId(id);
			   model.addAttribute("perfil", usuario);
               return "perfil/modificar-contraseña";
		} catch (ErrorException e) {
			System.out.println("Error: 191" + e.getMessage());
			return "perfil/perfil.html";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@PostMapping("/editar-contraseña")
	public String guardarContraseñaModificada(ModelMap model,HttpSession session,
	  @RequestParam String id,
	  @RequestParam("antiguaClave") String clave,
	  @RequestParam("claveNueva1") String claveNueva1,
	  @RequestParam("claveNueva2") String claveNueva2){

		String idRedirect = "";
		Usuario logueado = (Usuario) session.getAttribute("usuariosession");
		if (logueado == null || !logueado.getId().equals(id)) {
			return "redirect:/";
		}

		try {
			
			if(usuarioServicio.compararClavesNuevas(claveNueva1, claveNueva2)){
				Usuario usuario = usuarioServicio.buscarPorId(id);
				usuarioServicio.modificarClave(id, clave, claveNueva1);
				session.setAttribute("usuariosession", usuario);
				idRedirect = "?id=".concat(usuario.getId());
				return "redirect:/usuario/perfil".concat(idRedirect);
			}
			
		} catch (ErrorException e) {
			System.out.println("Error: 138" + e.getMessage());
			idRedirect = "?id=".concat(id);
			return "redirec:/usuario/perfil".concat(idRedirect);
		}

		return "redirec:/usuario/perfil?id=".concat(id);
	}

	

	@GetMapping("/recuperar")
	public String recuperacion() {

		return "recuperar-contra.html";

	}

	@PostMapping("/recuperar")
	public String recuperar(ModelMap model, @RequestParam String email) throws ErrorException {

		try {

			if (checkearEmail(email)) {

				usuarioServicio.recuperarContrasenia(email);

			}
		} catch (ErrorException e) {

			model.addAttribute("error", e.getMessage());

			return "recuperar-contra.html";

		}

		model.put("exito", "Se ha enviado tu nueva contraseña a tu mail. Luego podrás cambiarla en tu perfil.");
		
		return "login.html";

	}

	private Boolean checkearEmail(String email) throws ErrorException {
		Usuario u = usuarioServicio.buscarPorEmail(email);
		if (u == null) {
			throw new ErrorException("No hay nadie con ese mail.");
		}
		return true;

	}

	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@GetMapping("/listar")
	public String listaUsuarios(ModelMap model) {
		List<Usuario> usuarios = usuarioServicio.mostrarTodos();
		model.addAttribute("usuarios", usuarios);
		return "list-usuarios.html";
	}

}
