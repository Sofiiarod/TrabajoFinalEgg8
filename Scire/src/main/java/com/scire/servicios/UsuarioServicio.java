package com.scire.servicios;

import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
		

		return usuarioRepo.save(entidad);
		
	}
	
	//hago las validaciones necesarias para crear un nuevo usuario
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
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public Usuario buscarPorId(String id) throws ErrorException {
		Optional<Usuario> respuesta = usuarioRepo.findById(id);
		if ( respuesta.isPresent() ) {
			return respuesta.get();
		}else {
			throw new ErrorException ("No se pudo encontrar el creador solicitado");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public Usuario buscarPorNombre(String nombre) throws ErrorException {
		Usuario respuesta = usuarioRepo.buscarPorNombre(nombre);
		if ( respuesta != null ) {
			return respuesta;
		}else {
			throw new ErrorException ("No se pudo encontrar el creador solicitado");
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
	
	
	
	
}
