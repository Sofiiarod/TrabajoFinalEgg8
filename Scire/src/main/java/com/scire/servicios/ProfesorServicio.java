package com.scire.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.scire.entidades.Profesor;
import com.scire.errores.ErrorException;
import com.scire.repositorios.ProfesorRepositorio;

@Service
public class ProfesorServicio {

	@Autowired
	private ProfesorRepositorio profesorRepo;

	// REGISTRO Y GUARDADO DEL CREADOR.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public Profesor guardar(String nombre) throws ErrorException {

		validar(nombre);

		Profesor profesor = new Profesor();

		profesor.setNombre(nombre);
		
		profesor.setAlta(true);

		profesorRepo.save(profesor);
		
		return profesor;
		
	}

	// MOSTRAR TODOS LOS CREADORES.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public List<Profesor> mostrarTodos() throws ErrorException {
		return profesorRepo.findAll();

	}

	// BUSCA EL CREADOR POR ID, MODIFICAR EL NOMBRE Y GUARDAR LOS CAMBIOS.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public void modificar(String id, String nombre) throws ErrorException {

		Profesor profesor = buscarPorId(id);

		profesor.setNombre(nombre);

		profesorRepo.save(profesor);

	}

	// BUSCAR EL CREADOR POR ID Y ELIMINARLO DE LA BASE DE DATOS.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public void eliminar(String id) throws ErrorException {

		Profesor profesor = buscarPorId(id);

		profesorRepo.delete(profesor);

	}
	
	
	
	//BUSCAR EL CREADOR POR ID Y DARLO DE ALTA.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public void alta(String id) throws ErrorException {
		
		Profesor profesor = buscarPorId(id);
		
		profesor.setAlta(!profesor.getAlta());
		
		profesorRepo.save(profesor);
		
	}
	
	
	
	//BUSCAR EL CREADOR POR ID Y DARLO DE BAJA.
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
//	public void baja(String id, Boolean alta) throws ErrorException {
//		
//		Profesor profesor = buscarPorId(id);
//		
//		profesor.setAlta(false);
//		
//		profesorRepo.save(profesor);
//		
//		
//	}

	// BUSCAR LA ENTIDAD POR ID.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public Profesor buscarPorId(String id) throws ErrorException {

		Optional<Profesor> respuesta = profesorRepo.findById(id);

		if (respuesta.isPresent()) {

			return respuesta.get();

		} else {

			throw new ErrorException("No se pudo encontrar el creador solicitado");

		}

	}

	// BUSCAR LA ENTIDAD POR NOMBRE.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public Profesor buscarPorNombre(String nombre) throws ErrorException {

		Optional<Profesor> respuesta = profesorRepo.buscarPorNombre(nombre);

		if (respuesta.isPresent()) {

			return respuesta.get();

		} else {

			throw new ErrorException("No se pudo encontrar el creador solicitado");
		}
	}

	public void validar(String nombre) throws ErrorException {

		if (nombre == null || nombre.isEmpty()) {

			throw new ErrorException("El nombre del creador no puede ser nulo");

		}
	}
}
