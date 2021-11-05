package com.scire.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.scire.entidades.Categoria;
import com.scire.entidades.Curso;
import com.scire.entidades.Profesor;
import com.scire.entidades.Usuario;
import com.scire.errores.ErrorException;
import com.scire.repositorios.CategoriaRepositorio;
import com.scire.repositorios.CursoRepositorio;
import com.scire.repositorios.ProfesorRepositorio;
import com.scire.repositorios.UsuarioRepositorio;

@Service
public class CursoServicio {

	@Autowired
	private CursoRepositorio cursoRepo;
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	@Autowired
	private CategoriaRepositorio categoriaRepo;
	@Autowired
	private ProfesorRepositorio profesorRepo;

//	@Autowired
//    private UsuarioServicio usuarioService;

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

	// ELIMINAR
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

//LISTA TODOS
	@Transactional(readOnly = true)
	public List<Curso> listarTodos() {
		return cursoRepo.findAll();
	}

//INSCRIPCION (FALTA LA VALIDACION)!!!!!--!!!!!
	public void inscripcion(String id_usuario, String id_curso) {
		Curso curso = cursoRepo.getById(id_curso);
		Usuario usuario = usuarioRepo.getById(id_usuario);
		curso.getUsuarios().add(usuario);
		cursoRepo.save(curso);
	}

//DESINSCRIPCION
	public void desinscripcion(String id_usuario, String id_curso) {
		Curso curso = cursoRepo.getById(id_curso);
		Usuario usuario = usuarioRepo.getById(id_usuario);
		List<Usuario> usuarios = curso.getUsuarios();
		usuarios.removeIf(usu -> usu == usuario);
		cursoRepo.save(curso);
	}

//BUSCA CURSOS POR ESTADO
	public List<Curso> buscarPorEstado(Boolean estado) throws ErrorException {
		List<Curso> listaCursos = cursoRepo.findByEstado(estado);
		if (!listaCursos.isEmpty()) {
			return listaCursos;
		} else {
			if (estado == true) {
				throw new ErrorException("No hay cursos activos");
			} else {
				throw new ErrorException("No hay cursos inactivos");
			}
		}
	}

//BUSCAR POR NOMBRE	
	public Optional<Curso> buscarPorNombre(String nombre) throws ErrorException {
		Optional<Curso> listaCursos = cursoRepo.findByNombre(nombre);

		if (!listaCursos.isPresent()) {
			return listaCursos;
		} else {
			throw new ErrorException("No hay ningun curso con este nombre");
		}

	}

//BUSCAR POR CATEGORIA
	public List<Curso> buscarPorCategoria(String id_categoria) throws ErrorException {
		Categoria categoria = categoriaRepo.getById(id_categoria);
		List<Curso> listaCursos = cursoRepo.findByCategoria(categoria);

		if (!listaCursos.isEmpty()) {
			return listaCursos;
		} else {
			throw new ErrorException("No hay cursos para esta categoria");
		}
	}

//BUSCAR POR PROFESOR	
	public List<Curso> buscarProfesor(String id_profesor) throws ErrorException {
		Profesor profesor = profesorRepo.getById(id_profesor);
		List<Curso> listaCursos = cursoRepo.findByProfesor(profesor);

		if (!listaCursos.isEmpty()) {
			return listaCursos;
		} else {
			throw new ErrorException("No hay cursos para este profesor");
		}
	}

//BUSCAR CURSOS DE UN USUARIO
	public List<Curso> buscarCursosPorUsuario(String id_usuario) throws ErrorException {
		Usuario usuario = usuarioRepo.getById(id_usuario);
		List<Curso> listaCursos = cursoRepo.findByUsuarios(usuario);

		if (!listaCursos.isEmpty()) {
			return listaCursos;
		} else {
			throw new ErrorException("No hay cursos para este usuario");
		}
	}

}