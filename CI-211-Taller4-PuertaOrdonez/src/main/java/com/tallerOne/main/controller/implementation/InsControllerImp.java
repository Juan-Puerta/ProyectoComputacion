package com.tallerOne.main.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tallerOne.main.controller.interfaces.insController;
import com.tallerOne.main.model.Institution;
import com.tallerOne.main.model.Institution.addNewInstitution;
import com.tallerOne.main.services.InstitutionService;

@Controller
public class InsControllerImp implements insController{

	private InstitutionService insService;
	
	@Autowired
	public InsControllerImp(InstitutionService insService) {
		super();
		this.insService = insService;
	}

	@PreAuthorize("hasRole('Administrador' || 'Operador')")
	@RequestMapping(value = "/institucion/", method = { RequestMethod.GET, RequestMethod.POST })
	public String institutions(Model model) {
		model.addAttribute("institucion", insService.findAll());
		return "institucion/institucion";
	}
	
	@PreAuthorize("hasRole('Administrador' || 'Operador')")
	@GetMapping("/institucion/add")
	public String addIns(Model model) {
		model.addAttribute("institucion", new Institution());
		return "institucion/institucion-save";
	}
	
	@PreAuthorize("hasRole('Administrador' || 'Operador')")
	@PostMapping("/institucion/add")
	public String addIns(@Validated({addNewInstitution.class}) @ModelAttribute("institucion")Institution institucion, BindingResult result, 
			Model model, @RequestParam(value = "action", required = true) String action) {		
		if(action.equals("Cancelar")) {
			return "redirect:/institucion/";
		}
		if(result.hasErrors()) {
			return "institucion/institucion-save";
		}else if (!action.equals("Cancelar")) {
			try {
				insService.saveInstitution(institucion);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}else if (action.equals("Limpiar"))  {
			return "redirect:/institucion/add";
		}
		return "redirect:/institucion/";
	}
	
}
