package com.scire.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.scire.entidades.Categoria;
import com.scire.entidades.Profesor;
import com.scire.entidades.Curso;
import com.scire.entidades.Usuario;
import com.scire.errores.ErrorException;
import com.scire.repositorios.CursoRepositorio;

@Service
public class CursoServicio {

	@Autowired
	private CursoRepositorio cursoRepo;
	@Autowired
    private UsuarioServicio usuarioService;
	
	
	/**
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param url
	 * @param categoriaID el curso necesita una categoria
	 * @param creadorID   necesita llamar al id para crearse el curso necesita de un
	 *                    creador
	 * @throws ErrorException
	 * @param Usuario el curso no necesita un usuario para crearse
	 */
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public Curso guardar(String nombre, String descripcion, String url, Categoria categoriaID, Profesor profesorID)
			throws ErrorException {
		validar(nombre, descripcion, url, categoriaID, profesorID);
		Curso curso = new Curso();
		curso.setNombre(nombre);
		curso.setDescripcion(descripcion);
		curso.setUrl(url);
		curso.setEstado(true);
		curso.setCategoria(categoriaID);
		curso.setProfesor(profesorID);
		return cursoRepo.save(curso);
	}

	// MODIFICAR O ACTUALIZAR DATOS
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public Curso modificar(String id, String nombre, String descripcion, String url, Categoria categoriaid,
			Profesor profesorid) throws ErrorException {
		validar(nombre, descripcion, url, categoriaid, profesorid);
		Curso curso = encontrarPorID(id);
		curso.setNombre(nombre);
		curso.setDescripcion(descripcion);
		curso.setUrl(url);
		curso.setCategoria(categoriaid);
		curso.setProfesor(profesorid);
		return cursoRepo.save(curso);
	}
	//ELIMINAR
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void eliminar(String id) throws ErrorException {
		try {
			cursoRepo.deleteById(id);
		} catch (Exception e) {
			throw new ErrorException("error al eliminar el curso puede que no exista");
		}
	}
	// ALTA Y BAJA
	@Transactional
	public Curso alta(String id) throws ErrorException {
		Curso curso = encontrarPorID(id);
		curso.setEstado(true);
		return curso;
	}

	@Transactional
	public Curso baja(String id) throws ErrorException {
		Curso curso = encontrarPorID(id);
		curso.setEstado(false);
		return curso;
	}

	/**
	 * 
	 * @param id
	 * @return que traiga la respuesta para encontrar el id
	 * @throws ErrorException
	 */
	public Curso encontrarPorID(String id) throws ErrorException {
		Optional<Curso> respuesta = cursoRepo.findById(id);
		if (respuesta.isPresent()) {
			return respuesta.get();
		} else {
			throw new ErrorException("No existe un libro con ese ID");
		}
	}

	/**
	 * --------------------------- validaciones
	 */
	public void validar(String nombre, String descripcion, String url, Categoria categoria, Profesor profesor)
			throws ErrorException {

		if (nombre.isEmpty() || nombre == null || nombre.contains(" ")) {
			throw new ErrorException("Debe de indicarle un nombre");
		}
		if (descripcion.isEmpty() || descripcion == null || descripcion.contains(" ")) {
			throw new ErrorException("El Curso necesita una descripcion");
		}
		if (url.isEmpty() || url == null || url.contains(" ")) {
			throw new ErrorException("falta la url");
		}
		if (categoria == null) {
			throw new ErrorException("Debe de indicarle una categoria");
		}
		if (profesor == null) {
			throw new ErrorException("Debe de indicarle un profesor");
		}
	}

//QUERY , ENCONTRAR EN EL REPOSITORIO
	@Transactional(readOnly = true)
	public List<Curso> listarTodos() {
		return cursoRepo.findAll();
	}
@Transactional(readOnly = true)
public List<Curso> listarPorNombre(String nombre){
	return cursoRepo.buscarPorNombre(nombre);
}
//@Transactional(readOnly = true)
//public List<Curso> encontrarporUsuario(String idUsuario) throws ErrorException{
//	
//       Usuario u =  usuarioService.buscarPorId(idUsuario);
//       
//       return cursoRepo.buscarPorUsuario(u.toString());
//}
}
