package com.scire.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.scire.entidades.Profesor;

@Repository
public interface ProfesorRepositorio extends JpaRepository<Profesor,String> {
	@Query ("SELECT c FROM Profesor c WHERE c.nombre = :nombre")
	public Optional<Profesor> buscarPorNombre (@Param("nombre") String nombre);
}
