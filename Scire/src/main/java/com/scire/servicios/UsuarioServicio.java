package com.scire.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.scire.entidades.Foto;
import com.scire.entidades.Usuario;
import com.scire.errores.ErrorException;
import com.scire.repositorios.UsuarioRepositorio;
import com.scire.roles.Rol;

@Service
public class UsuarioServicio implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio usuarioRepo;

	@Autowired
	private NotificacionServicio notificacionServ;
	@Autowired
	private FotoServicio fotoServicio;

	// CREA UN NUEVO USUARIO Y LO GUARDA EN LA BASE DE DATOS SI ES POSIBLE

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Usuario guardar(MultipartFile archivo, String nombre, String apellido, String email, String clave,
			String clave2) throws ErrorException {

		validar(nombre, apellido, email, clave, clave2);

		Usuario entidad = new Usuario();

		entidad.setNombre(nombre);
		entidad.setApellido(apellido);
		entidad.setEmail(email);

//		String claveEncriptada = new BCryptPasswordEncoder().encode(clave);
//		entidad.setClave(claveEncriptada);
		entidad.setClave(new BCryptPasswordEncoder().encode(clave));

		entidad.setRol(Rol.USER);
		entidad.setAlta(true);
		entidad.setFechaCreado(new Date());

		if( archivo == null ) {
			Foto foto = fotoServicio.guardar(null);
			entidad.setFoto(foto);
		}else {
			Foto foto = fotoServicio.guardar(archivo);
			entidad.setFoto(foto);
		}
		
		

//		notificacionServ.enviar("Bievenido a la comunidad de Scire", "Scire.edu", entidad.getEmail());

		
		this.mailBienvenida(entidad);		


		return usuarioRepo.save(entidad);

	}



	
	public void mailBienvenida(Usuario entidad) {
		
		String titulo= "Bienvenido a la comunidad Scire";
		String cuerpo="Hola "+ entidad.getNombre()+ ","+"\n"+"\n"+
		"Aprende participando,\r\n"
		+ "accede a los mejores cursos online."+"\n"
		+ "Interactúa con los mejores profesionales y descubre un mundo de posibilidades.\r\n"+"\n"
		+ "Estamos para responderte todas tus dudas."+ "\n"
		+"Saludos!"+"\n"+
		"Comunidad Scire";
		
		notificacionServ.enviar(cuerpo, titulo, entidad.getEmail());
		
	}
	
	
	
	//HAGO LAS VALIDACIONES NECESARIAS PARA CREAR EL USUARIO
	public void validar(String nombre, String apellido, String email, String clave, String clave2) throws ErrorException {
		
		if(!clave.equals(clave2)) {

			throw new ErrorException("Las claves no coinciden");
		}

		if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
			throw new ErrorException("Debe tener un nombre valido");
		}

		if (apellido == null || apellido.isEmpty() || apellido.contains("  ")) {
			throw new ErrorException("Debe tener un apellido valido");
		}

		if (email == null || email.isEmpty() || email.contains("  ")) {
			throw new ErrorException("Debe tener un email valido");
		}


<<<<<<< HEAD
		if (usuarioRepo.buscarPorEmail(email) != null) {
			throw new ErrorException("El Email ya esta en uso");
=======
	if (!usuarioRepo.findByEmail(email).isEmpty()) {
		throw new ErrorException("El Email ya esta en uso");
>>>>>>> 4b11185e1170f50ddb0fa14b6f4432603c995130
		}

		// la clave no debe ser nula, no debe estar vacia, no debe contener espacios,
		// debe tener entre 8 y 12 caracteres

		if (clave == null || clave.length() < 8) {
			throw new ErrorException("Debe tener una clave valida");
		}

	}

	// ES PARA CAMBIAR EL ESTADO DEL USUARIO
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void altaBaja(String id) throws ErrorException {
		try {
			Usuario a = usuarioRepo.getById(id);
			a.setAlta(!a.getAlta());
			usuarioRepo.save(a);
		} catch (Exception e) {
			throw new ErrorException("No se pudo modificar el estado del alta");
		}
	}

	// BUSCA UN USUARIO POR ID, SI LO ENCUENTRA LO DEVUELVE
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public Usuario buscarPorId(String id) throws ErrorException {
		Optional<Usuario> respuesta = usuarioRepo.findById(id);
		if (respuesta.isPresent()) {
			return respuesta.get();
		} else {
			throw new ErrorException("No se pudo encontrar el usuario solicitado");
		}
	}

	// BUSCA UN USUARIO POR SU NOMBRE Y SI LO ENCUENTRA LO RETORNA
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public Usuario buscarPorNombre(String nombre) throws ErrorException {
		Usuario respuesta = usuarioRepo.buscarPorNombre(nombre);
		if (respuesta != null) {
			return respuesta;
		} else {
			throw new ErrorException("No se pudo encontrar el usuario solicitado");
		}
	}

	// BUSCA UN USUARIO POR SU EMAIL Y SI LO ENCUENTRA LO RETORNA
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public Usuario buscarPorEmail(String email) throws ErrorException {
		Usuario respuesta = usuarioRepo.buscarPorEmail(email);
		if (respuesta != null) {
			return respuesta;
		} else {
			throw new ErrorException("No se pudo encontrar el usuario solicitado");
		}
	}

	// MOSTRAR TODOS LOS USUARIOS
	@Transactional(readOnly = true)
	public List<Usuario> mostrarTodos() {

		return usuarioRepo.findAll();
	}

	// ELIMINAR USUARIO POR ID
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public void eliminar(String id) throws ErrorException {

		usuarioRepo.deleteById(id);
	}

	// MODIFICAR USUARIO

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public void modificar(MultipartFile archivo,String id, String nombre, String apellido,String email, String clave, String clave2)
			throws ErrorException {
		try {
			validar(nombre, apellido, email, clave, clave2);
			Usuario entidad = usuarioRepo.getById(id);
			entidad.setNombre(nombre);
			entidad.setApellido(apellido);
			entidad.setEmail(email);
			String encriptada = new BCryptPasswordEncoder().encode(clave);
			entidad.setClave(encriptada);
			String idFoto = null;
			if(entidad.getFoto() != null) {
				idFoto = entidad.getFoto().getId();
			}
			Foto foto = fotoServicio.actualizar(idFoto, archivo);
			entidad.setFoto(foto);
			usuarioRepo.save(entidad);
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			throw new ErrorException("No se pudieron modifcar los datos del usuario");
		}


	}

		//MODIFICAR CONTRASENIA
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
		public void modificarClave(String id, String clave, String claveNueva) throws ErrorException {
			
			Usuario entidad = usuarioRepo.getById(id);
			
			//COMPARAMOS LAS CLAVES https://www.example-code.com/java/bcrypt_verify_password.asp
			 boolean passwordValid = BCrypt.checkpw(clave, entidad.getClave());
			 
			 if (passwordValid == true) {
				 
					if (claveNueva == null || claveNueva.isEmpty() || claveNueva.contains("  ") || claveNueva.length() < 8 || claveNueva.length() > 12) {
						throw new ErrorException("La clave debe tener entre 8 y 12 caracteres");
					   }else {
						   
						  String claveEncriptada = new BCryptPasswordEncoder().encode(claveNueva);
						  entidad.setClave(claveEncriptada);
						   
						  usuarioRepo.save(entidad);
					  }	
			}else {
			       throw new ErrorException ("La clave actual no es la correcta");
			      }	
		}
	
		
			
		

	

	@Transactional
	public void recuperarContrasenia(String mail) throws ErrorException {
		try {

			String claveNueva = UUID.randomUUID().toString();
			String claveNuevaEncriptada = new BCryptPasswordEncoder().encode(claveNueva);

			Usuario entidad = this.buscarPorEmail(mail);
			entidad.setClave(claveNuevaEncriptada);
			usuarioRepo.save(entidad);
			notificacionServ.enviarModificarContraseña("", "Recuperación de contraseña", mail, claveNueva);
		} catch (Exception e) {
			throw new ErrorException("error");
		}

	}

	@Override

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Usuario user = usuarioRepo.buscarPorEmail(email);

		if (user != null) {
			List<GrantedAuthority> permissions = new ArrayList<>();
			GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRol().toString());
			permissions.add(p);
			// Una vez que el usuario pudo entrar a inicio hace una llamada al HttpSession
			// que pide los atributos del Http
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			// Aca una vez que obtubo los atributos del http inicia una session
			HttpSession session = attr.getRequest().getSession(true);
			// y aca se le da un nombre con el que se va a utilizar ese usuario ya
			// autenticado y que pudo entrar a inicio
			// el usuariosession es para usar en thymeleaf si solo si el usuario esta
			// autenticado y pudo loguearse
			session.setAttribute("usuariosession", user);
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getClave(),
					permissions);
		}
		return null;

	}
}
