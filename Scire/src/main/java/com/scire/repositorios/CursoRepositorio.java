package com.scire.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scire.entidades.Curso;


@Repository
public interface CursoRepositorio extends JpaRepository<Curso,String> {

	@Query("SELECT a from Curso a WHERE a.nombre LIKE :nombre")
	public List<Curso> buscarPorNombre(@Param("nombre") String nombre);
	
	@Query("SELECT a from Curso a WHERE a.categoria LIKE :categoria")
	public List<Curso> buscarPorCategoria(@Param("categoria") String categoria);
	

	@Query("SELECT a from Curso a WHERE a.profesor LIKE :profesor")
	public List<Curso> buscarPorCreador(@Param("profesor") String profesor);
	

	
}
