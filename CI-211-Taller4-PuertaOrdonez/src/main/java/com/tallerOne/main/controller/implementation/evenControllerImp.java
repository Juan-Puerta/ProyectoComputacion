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

import com.tallerOne.main.controller.interfaces.EvenController;
import com.tallerOne.main.model.Eventstatus;
import com.tallerOne.main.model.Eventstatus.addNewEventStatus;
import com.tallerOne.main.services.EstadoService;

@Controller
public class evenControllerImp implements EvenController{

	private EstadoService estService;

	@Autowired
	public evenControllerImp(EstadoService estService) {
		super();
		this.estService = estService;
	}
	
	@PreAuthorize("hasRole('Administrador' || 'Operador')")
	@RequestMapping(value = "/estado/", method = { RequestMethod.GET, RequestMethod.POST })
	public String eventStatusMain(Model model) {
		model.addAttribute("estado", estService.findAll());
		return "estado/estado";
	}
	
	@PreAuthorize("hasRole('Administrador' || 'Operador')")
	@GetMapping("/estado/add")
	public String addEvent(Model model) {
		model.addAttribute("estado", new Eventstatus());
		return "estado/estado-save";
	}
	
	@PreAuthorize("hasRole('Administrador' || 'Operador')")
	@PostMapping("/estado/add")
	public String addEvent(@Validated({addNewEventStatus.class}) @ModelAttribute("estado")Eventstatus estado, BindingResult result, 
			Model model, @RequestParam(value = "action", required = true) String action) {		
		if(action.equals("Cancelar")) {
			return "redirect:/estado/";
		}
		if(result.hasErrors()) {
			return "estado/estado-save";
		}else if (!action.equals("Cancelar")) {
			try {
				estService.save(estado);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}else if (action.equals("Limpiar"))  {
			return "redirect:/estado/add";
		}
		return "redirect:/estado/";
	}
	
}
