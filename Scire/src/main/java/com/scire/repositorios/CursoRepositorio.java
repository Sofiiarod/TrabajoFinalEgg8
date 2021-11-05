package com.scire.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scire.entidades.Categoria;
import com.scire.entidades.Curso;
import com.scire.entidades.Profesor;
import com.scire.entidades.Usuario;

@Repository
public interface CursoRepositorio extends JpaRepository<Curso,String> {

	
	public List<Curso> findByEstado(Boolean estado);
	
	public Optional<Curso> findByNombre(String nombre);
	
	public List<Curso> findByCategoria(Categoria categoria);
	
	public List<Curso> findByProfesor(Profesor profesor);
	
	public List<Curso> findByUsuarios(Usuario usuario);
	
//	@Query("SELECT c FROM curso c WHERE c.categoria_id= :categoria AND c.estado=1")
//	public List<Curso> buscarCategoriaActivas(@Param("categoria") String id_categoria);

//	@Query("SELECT c FROM curso c WHERE c.profesor_id= :profesor AND c.estado=1")
//	public List<Curso> buscarPorProfesorActivo(@Param("profesor") String id_profesor);


	
}
