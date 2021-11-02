package com.scire.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/")
public class MainControlador {
	
	
	
	@GetMapping("/")
	public String index() {
	return "../template/index.html";
	
	}
	
	
		
	
	
	
	
//	@GetMapping()
//	public String index() {
//		return "index.html"; //tenemos que poner el html al cual queremos ir 
//	}
//	

	
}
	


