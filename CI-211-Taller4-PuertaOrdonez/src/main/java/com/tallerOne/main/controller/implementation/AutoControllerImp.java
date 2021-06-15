package com.tallerOne.main.controller.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tallerOne.main.controller.interfaces.AutoController;
import com.tallerOne.main.model.Autotransition;
import com.tallerOne.main.services.AutotransitionService;
import com.tallerOne.main.services.EstadoService;
import com.tallerOne.main.services.InstitutionService;
import com.tallerOne.main.validations.ValidacionDos;
import com.tallerOne.main.validations.ValidacionUno;

@Controller
public class AutoControllerImp implements AutoController{

	private AutotransitionService autoService;
	private InstitutionService insService;
	private EstadoService estService;

	@Autowired
	public AutoControllerImp(AutotransitionService autoService, InstitutionService insService, EstadoService estService) {
		super();
		this.autoService = autoService;
		this.insService = insService;
		this.estService = estService;
	}
	
	@PreAuthorize("hasRole('Administrador'||'Operador')")
	@RequestMapping(value = "/autotransicion/", method = { RequestMethod.GET, RequestMethod.POST })
	public String auto(Model model) {
		model.addAttribute("autotransicion", autoService.findAll());
		return "autotransicion/autotransicion";
	}
	
	//----------------------Guardar-----------------------------//
	
	@PreAuthorize("hasRole('Administrador')")
	@GetMapping("/autotransicion/add")
	public String addAuto(Model model) {
		model.addAttribute("autotransicion", new Autotransition());
		model.addAttribute("instituciones", insService.findAll());
		model.addAttribute("estados", estService.findAll());
		return "autotransicion/autotransicion-save";
		//Falta por hacer
	}
	
	@PreAuthorize("hasRole('Administrador')")
	@PostMapping("/autotransicion/add")
	public String addAuto(@Validated({ValidacionDos.class,ValidacionUno.class}) @ModelAttribute("autotransicion")Autotransition autotransicion, BindingResult result, 
			Model model, @RequestParam(value = "action", required = true) String action) {		
		if(action.equals("Cancelar")) {
			return "redirect:/autotransicion/";
		}
		if(result.hasErrors()) {
			model.addAttribute("instituciones", insService.findAll());
			model.addAttribute("estados", estService.findAll());
			return "autotransicion/autotransicion-save";
			//Falta por hacer
		}else if (!action.equals("Cancelar")) {
			try {
				autoService.save(autotransicion);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}else if (action.equals("Limpiar"))  {
			return "redirect:/autotransicion/add";
		}
		return "redirect:/autotransicion/";
		//return "autotransicion/autotransicion";
	}
	
	//----------------------Editar-----------------------------//
	
	@PreAuthorize("hasRole('Administrador')")
	@GetMapping("/autotransicion/edit/{id}")
	public String showUpdate(@PathVariable("id") long id, Model model) {
		Optional<Autotransition> auto = autoService.findAutotransition(id);
		if (auto == null)
			throw new IllegalArgumentException("Invalid appointment Id:" + id);
		model.addAttribute("autotransicion", auto.get());
		model.addAttribute("instituciones", insService.findAll());
		model.addAttribute("estados", estService.findAll());
		return "autotransicion/autotransicion-edit";
	}
		
	@PreAuthorize("hasRole('Administrador')")
	@PostMapping("/autotransicion/edit/{id}")
	public String update(@PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action,
			@Validated({ValidacionDos.class,ValidacionUno.class})  @ModelAttribute("autotransicion") Autotransition autotransicion, BindingResult bindingResult, Model model) {
		if(action.equals("Cancelar")) {
			return "redirect:/autotransicion/";
		}else if (bindingResult.hasErrors()) {			
			Optional<Autotransition> auto2 = autoService.findAutotransition(id);
			if (auto2 == null)
				throw new IllegalArgumentException("Invalid institution Id:" + id);
			model.addAttribute("instituciones", insService.findAll());
			model.addAttribute("estados", estService.findAll());
			return "autotransicion/autotransicion-edit";
		}else if (!action.equals("Cancelar")) {
			try{
				Optional<Autotransition> auto2 = autoService.findAutotransition(id);
				auto2.get().setAutotranName(autotransicion.getAutotranName());
				auto2.get().setAutotranIsactive(autotransicion.getAutotranIsactive());
				auto2.get().setAutotranLogicaloperand(autotransicion.getAutotranLogicaloperand());
				//auto2.get().setInstInstId(autotransicion.getInstInstId());
				auto2.get().setInstInstId(autotransicion.getInstInstId());
				auto2.get().setEventstatus1(autotransicion.getEventstatus1());
				autoService.saveAutotransition(auto2.get());
				model.addAttribute("autotransicion", autoService.findAll());
				return "redirect:/autotransicion/";
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		return "redirect:/autotransicion/";
	}
	
	//----------------------Consultar-----------------------------//
	
	@PreAuthorize("hasRole('Administrador')")
	@RequestMapping(value = "/view/autotransicion/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String show(@PathVariable("id") long id, Model model) {
		Optional<Autotransition> auto = autoService.findAutotransition(id);
		if (auto == null)
			throw new IllegalArgumentException("Invalid appointment Id:" + id);
		model.addAttribute("autotransicion", auto.get());
		return "autotransicion/autotransicion-view";
	}
	
}
