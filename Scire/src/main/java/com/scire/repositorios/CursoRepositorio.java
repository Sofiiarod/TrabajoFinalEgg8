package com.scire.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scire.entidades.Categoria;
import com.scire.entidades.Curso;
import com.scire.entidades.Profesor;
import com.scire.entidades.Usuario;

@Repository
public interface CursoRepositorio extends JpaRepository<Curso,String> {


//	@Query("SELECT a from Curso a WHERE c.estado=1 AND a.nombre LIKE :nombre")
//	public List<Curso> buscarPorNombreActivos(@Param("nombre") String nombre);
	
	@Query("SELECT a from Curso a WHERE a.nombre LIKE :nombre")
	public List<Curso> buscarPorNombre(@Param("nombre") String nombre);
//	
//	@Query("SELECT a from Curso a WHERE a.estado=1")
//	public List<Curso> buscarActivos();
//	
//	@Query("SELECT c FROM curso c WHERE c.categoria_id= :categoria AND c.estado=1")
//	public List<Curso> buscarCategoriaActivas(@Param("categoria") String id_categoria);
//	
//	@Query("SELECT c FROM curso c WHERE c.categoria_id= :categoria")
//	public List<Curso> buscarCategoria(@Param("categoria") String id_categoria);
//	
//	@Query("SELECT c FROM curso c WHERE c.profesor_id= :profesor AND c.estado=1")
//	public List<Curso> buscarPorProfesorActivo(@Param("profesor") String id_profesor);
//	
//	@Query("SELECT c FROM curso c WHERE EXISTS (SELECT * FROM curso_usuarios cu WHERE cu.curso_id=c.id AND cu.usuarios_id = :id_usuario")
//	public List<Curso> buscarCursosDelUsuario(@Param("id_usuario") String id_usuario);


	
	public List<Curso> findByEstado(Boolean estado);
	
	public Optional<Curso> findByNombre(String nombre);
	
	public List<Curso> findByCategoria(Categoria categoria);
	
	public List<Curso> findByProfesor(Profesor profesor);
	
	public List<Curso> findByUsuarios(Usuario usuario);
	
	@Query("SELECT c FROM Curso c WHERE c.estado = true AND c.categoria = :categoria")
	public List<Curso> buscarCursosActivosPorCategoria(@Param("categoria") Categoria categoria);
	
	@Query("SELECT c FROM Curso c WHERE c.estado = true AND c.profesor = :profesor")
	public List<Curso> buscarCursosActivosPorProfesor(@Param("profesor") Profesor profesor);

	
}
