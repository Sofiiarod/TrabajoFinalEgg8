package com.scire.controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.scire.errores.ErrorException;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
@RequestMapping("/consultas")

public class ConsultasControlador {

	@GetMapping("/consultas")
	public String consulta() {
		return "form-consulta.html";
		
	}
	
	@PostMapping("/consultar")
	public String consultar(ModelMap model,@RequestParam String consulta) throws ErrorException{
		try {
			
			model.put("exito", "Su consulta se ha enviado");
			
		} catch (Exception e) {
			
			model.put("error", e.getMessage());
			return "index-menu-vertical.html";
			
		}
		
		return "index.html";

	}

}
