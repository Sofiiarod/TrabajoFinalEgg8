package com.scire.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.scire.entidades.Creador;

@Repository
public interface CreadorRepositorio extends JpaRepository<Creador,String> {
	
	@Query ("SELECT c FROM Creador c WHERE c.nombre = :nombre")
	public Creador buscarPorNombre (@Param("nombre") String nombre);
}
