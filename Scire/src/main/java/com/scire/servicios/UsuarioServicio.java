package com.scire.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.scire.entidades.Usuario;
import com.scire.errores.ErrorException;
import com.scire.repositorios.UsuarioRepositorio;
import com.scire.roles.Rol;

@Service
public class UsuarioServicio {
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
<<<<<<< HEAD
=======
	@Autowired
	private NotificacionServicio notificacionServ;
	
	
	// CREA UN NUEVO USUARIO Y LO GUARDA EN LA BASE DE DATOS SI ES POSIBLE
>>>>>>> bcd7500b933da6df5b7b6fbed8b5538849f4f2fb
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public Usuario guardar(String nombre, String apellido, String email, String clave, String clave2) throws ErrorException {

		validar(nombre, apellido, email, clave, clave2);

		Usuario entidad = new Usuario();

		entidad.setNombre(nombre);
		entidad.setApellido(apellido);
		entidad.setEmail(email);
		entidad.setClave(new BCryptPasswordEncoder().encode(clave));
		entidad.setRol(Rol.USER);
		entidad.setAlta(true);
		entidad.setFechaCreado(new Date());
		
		notificacionServ.enviar("Bievenido a la comunidad de Scire", "Scire.edu", entidad.getEmail());


		return usuarioRepo.save(entidad);
	
	}
	
	//HAGO LAS VALIDACIONES NECESARIAS PARA CREAR EL USUARIO
	public void validar(String nombre, String apellido, String email, String clave, String clave2) throws ErrorException {
		
		if(clave != clave2) {
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

		if (usuarioRepo.buscarPorEmail(email) != null) {
			throw new ErrorException("El Email ya esta en uso");
		}	
		//la clave no debe ser nula, no debe estar vacia, no debe contener espacios, debe tener entre 8 y 12 caracteres
		
		if (clave == null || clave.isEmpty() || clave.contains("  ") || clave.length() < 8 || clave.length() > 12) {
			throw new ErrorException("Debe tener una clave valida");
		}

	}

	// ES PARA CAMBIAR EL ESTADO DEL USUARIO
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
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
		if ( respuesta.isPresent() ) {
			return respuesta.get();
		}else {
			throw new ErrorException ("No se pudo encontrar el usuario solicitado");
		}
	}
	
    // BUSCA UN USUARIO POR SU NOMBRE Y SI LO ENCUENTRA LO RETORNA
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public Usuario buscarPorNombre(String nombre) throws ErrorException {
		Usuario respuesta = usuarioRepo.buscarPorNombre(nombre);
		if ( respuesta != null ) {
			return respuesta;
		}else {
			throw new ErrorException ("No se pudo encontrar el usuario solicitado");
		}
	}
	
	   // BUSCA UN USUARIO POR SU EMAIL Y SI LO ENCUENTRA LO RETORNA
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
		public Usuario buscarPorEmail(String email) throws ErrorException {
			Usuario respuesta = usuarioRepo.buscarPorEmail(email);
			if ( respuesta != null ) {
				return respuesta;
			}else {
				throw new ErrorException ("No se pudo encontrar el usuario solicitado");
			}
		}
		
		// MOSTRAR TODOS LOS USUARIOS
		@Transactional(readOnly =true)
		public List<Usuario> mostrarTodos(){
			
			return usuarioRepo.findAll();
		}
		
		// ELIMINAR USUARIO POR ID
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
		public void eliminar(String id) throws ErrorException {
			
			usuarioRepo.deleteById(id);
		}
		
		
		//MODIFICAR USUARIO
		
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
		public void modificar(String id, String nuevonombre, String nuevoapellido, String nuevoemail) throws ErrorException {
			try {
			Usuario entidad = usuarioRepo.getById(id);
			entidad.setNombre(nuevonombre);
			entidad.setApellido(nuevoapellido);
			usuarioRepo.save(entidad);
			}catch(Exception e) {
				throw new ErrorException ("No se pudieron modifcar los datos del usuario");
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
						  entidad.setClave(new BCryptPasswordEncoder().encode(claveNueva));
						  usuarioRepo.save(entidad);
					  }	
			}else {
			       throw new ErrorException ("La clave actual no es la correcta");
			      }	
		}
		
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
		public boolean inicioSesion(String email, String claveingresada) throws ErrorException {
		
			try {
			Usuario entidad = this.buscarPorEmail(email);
			if(entidad==null){
				throw new ErrorException ("El usuario no existe");
			}
			
			//COMPARAMOS LAS CLAVES https://www.example-code.com/java/bcrypt_verify_password.asp
			 
			
			boolean passwordValid = BCrypt.checkpw(claveingresada, entidad.getClave());
			 if (passwordValid == true) {
				 return passwordValid;
			}else {
			       throw new ErrorException ("La clave no es la correcta");
			       }	
			}catch(Exception e) {
				throw new ErrorException ("Error al iniciar sesion");
			}
		}
		
		@Transactional
	    public void recuperarContraseña(String mail) throws ErrorException {
			try {

	        String claveNueva = UUID.randomUUID().toString();
	        String claveNuevaEncriptada = new BCryptPasswordEncoder().encode(claveNueva);
	        
	        Usuario entidad = this.buscarPorEmail(mail);
	        entidad.setClave(claveNuevaEncriptada);
	        usuarioRepo.save(entidad);
	        notificacionServ.enviarModificarContraseña("", "Recuperación de contraseña", mail, claveNueva);
			} catch(Exception e) {
				throw new ErrorException ("error");
			}
			
	    }


	
		  

	
}	
	
	
//PARA INVESTIGAR, AGUSTINFIORDE EN PERROS V2
	
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		
//		Usuario user = usuarioRepository.buscarPorEmail(email);
//		
//		if (user != null) {
//			List<GrantedAuthority> permissions = new ArrayList<>();
//			GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRol().toString());
//			permissions.add(p);
//			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//			HttpSession session = attr.getRequest().getSession(true);
//			session.setAttribute("usuario", user);
//			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getClave(),
//					permissions);
//		}
//		return null;
//
//	}
	
	
	
	

