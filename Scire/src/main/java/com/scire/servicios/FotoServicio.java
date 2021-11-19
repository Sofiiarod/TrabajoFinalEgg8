package com.scire.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scire.entidades.Foto;
import com.scire.errores.ErrorException;
import com.scire.repositorios.FotoRepositorio;


@Service
public class FotoServicio {

	@Autowired
	private FotoRepositorio fotoRepositorio;
	public Foto guardar(MultipartFile archivo) throws ErrorException {
		if (archivo != null) {
			try {

				Foto foto = new Foto();
				foto.setMime(archivo.getContentType());
				foto.setNombre(archivo.getName());
				foto.setContenido(archivo.getBytes());
				return fotoRepositorio.save(foto);

			} catch (Exception e) {
				throw new ErrorException("No se ha podido cargar la foto");
			}

		} else {
			return null;
		}
	}
	public Foto actualizar(String idFoto, MultipartFile archivo) throws ErrorException {
		if (archivo != null) {
			try {

				Foto foto = new Foto();
				
				if (idFoto != null) {
					Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
					if (respuesta.isPresent()) {
						foto = respuesta.get();
					}
				}
				foto.setMime(archivo.getContentType());
				foto.setNombre(archivo.getName());
				foto.setContenido(archivo.getBytes());
				return fotoRepositorio.save(foto);

			} catch (Exception e) {
				throw new ErrorException("No se ha podido cargar la foto");
			}

		} else {
			return null;
		}
	}
	
}
