package com.tallerOne.main.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tallerOne.main.controller.interfaces.AppController;

@Controller
public class AppControllerImp implements AppController{

	@Autowired
	public AppControllerImp() {
		super();
	}

	@PreAuthorize("hasRole('Administrador'||'Operador')")
	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		return "index-login";
	}
	
}
