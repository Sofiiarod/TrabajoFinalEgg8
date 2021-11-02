package com.scire.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.scire.entidades.Categoria;
import com.scire.errores.ErrorException;
import com.scire.repositorios.CategoriaRepositorio;

@Service
public class CategoriaServicio {

	@Autowired
	private CategoriaRepositorio categoriaRepo;

	// REGISTRO Y GUARDADO DEL CATEGORIA.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public void guardar(String nombre) throws ErrorException {

		validar(nombre);

		Categoria categoria = new Categoria();

		categoria.setNombre(nombre);

		categoriaRepo.save(categoria);

	}

	// MOSTRAR TODAS LAS CATEGORÍAS.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public List<Categoria> mostrarTodos(String id) throws ErrorException {

		return categoriaRepo.findAll();

	}

	// BUSCA LA CATEGORÍA POR ID, MODIFICAR EL NOMBRE Y GUARDAR LOS CAMBIOS.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public void modificar(String id, String nombre) throws ErrorException {

		Categoria categoria = buscarPorId(id);

		categoria.setNombre(nombre);

		categoriaRepo.save(categoria);

	}

	// BUSCAR LA CATEGORÍA POR ID Y ELIMINARLA DE LA BASE DE DATOS.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public void eliminar(String id) throws ErrorException {

		Categoria categoria = buscarPorId(id);

		categoriaRepo.delete(categoria);

	}

	// BUSCAR LA ENTIDAD POR ID.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public Categoria buscarPorId(String id) throws ErrorException {

		Optional<Categoria> respuesta = categoriaRepo.findById(id);

		if (respuesta.isPresent()) {

			return respuesta.get();

		} else {

			throw new ErrorException("No se pudo encontrar el creador solicitado");

		}

	}

	// BUSCAR LA ENTIDAD POR NOMBRE.
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class })
	public Categoria buscarPorNombre(String nombre) throws ErrorException {

		Optional<Categoria> respuesta = categoriaRepo.buscarPorNombre(nombre);

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
