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
