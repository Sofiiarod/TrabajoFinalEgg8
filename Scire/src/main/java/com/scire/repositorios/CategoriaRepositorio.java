package com.scire.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scire.entidades.Categoria;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria,String> {
	@Query ("SELECT c FROM Categoria c WHERE c.nombre = :nombre")
	public Categoria buscarPorNombre (@Param("nombre") String nombre);
}
