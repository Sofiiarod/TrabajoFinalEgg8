package com.scire.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.scire.entidades.Creador;
import com.scire.errores.ErrorException;
import com.scire.repositorios.CreadorRepositorio;


@Service
public class CreadorServicio {
	
	@Autowired
	private CreadorRepositorio creadorRepo;
	
	// REGISTRO Y GUARDADO DEL CREADOR.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public void guardar (String nombre) throws ErrorException {
		
		if(nombre == null || nombre.isEmpty()) {
			
			throw new ErrorException ("El nombre del creador no puede ser nulo");
			
		}
		
		Creador creador = new Creador();
		
		creador.setNombre(nombre);
		
		creadorRepo.save(creador);
		
	}
	
	// MOSTRAR TODOS LOS CREADORES.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public List<Creador> mostrarTodos (String id) throws ErrorException {
		
		return creadorRepo.findAll();
		
	}
	
	// BUSCA EL CREADOR POR ID, MODIFICAR EL NOMBRE Y GUARDAR LOS CAMBIOS.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public void modificar (String id, String nombre) throws ErrorException {
		
		Creador creador = buscarPorId(id);
			
		creador.setNombre(nombre);

		creadorRepo.save(creador);
		
	}
	
	//BUSCAR EL CREADOR POR ID Y ELIMINARLO DE LA BASE DE DATOS.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public void eliminar (String id) throws ErrorException {
		
		Creador creador = buscarPorId(id);
		
		creadorRepo.delete(creador);
		
	}
	
	//BUSCAR POR ID LA ENTIDAD.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public Creador buscarPorId(String id) throws ErrorException {
		
		Optional<Creador> respuesta = creadorRepo.findById(id);
		
		if ( respuesta.isPresent() ) {
			
			return respuesta.get();
			
		}else {
			
			throw new ErrorException ("No se pudo encontrar el creador solicitado");
			
		}
		
	}
	
	public void guardarCambios (Creador creador) throws ErrorException { 
		creadorRepo.save(creador);
	}
	
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
//	public void buscarPorNombre(String nombre) {
//		Optional<Creador> respuesta = creadorRepo.buscarPorNombre(nombre);
//		if (respuesta.isPresent()) {
//			return respuesta.get();
//	} else {
//			throw new ErrorException ("No se ha encontrado el creador solicitada");
//	}
//	}
	
}
