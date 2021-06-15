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

import com.tallerOne.main.controller.interfaces.PreController;
import com.tallerOne.main.model.Precondition;
import com.tallerOne.main.services.AutotransitionService;
import com.tallerOne.main.services.PreconditionService;
import com.tallerOne.main.validations.ValidacionUno;

@Controller
public class PreControllerImp implements PreController{

	private PreconditionService preService;
	private AutotransitionService autoService;

	@Autowired
	public PreControllerImp(PreconditionService preService, AutotransitionService autoService) {
		super();
		this.preService = preService;
		this.autoService = autoService;
	}
	
	@GetMapping("/precondicion/")
	@PreAuthorize("hasRole('Administrador'||'Operador')")
	@RequestMapping(value = "/precondicion/", method = { RequestMethod.GET, RequestMethod.POST })
	public String preconMain(Model model){
		model.addAttribute("precondicion", preService.findAll());
		return "precondicion/precondicion";
	}
	
	//-----------------------Guardar----------------------------
	
	@PreAuthorize("hasRole('Operador')")
	@GetMapping("/precondicion/add")
	public String addPre(Model model){
		model.addAttribute("precondicion", new Precondition());
		model.addAttribute("autotransiciones", autoService.findAll());
		return "precondicion/precondicion-save";
	}
	
	@PreAuthorize("hasRole('Operador')")
	@PostMapping("/precondicion/add")
	public String addPre(@Validated({ValidacionUno.class}) @ModelAttribute("precondicion")Precondition precondicion, BindingResult result, 
			Model model, @RequestParam(value = "action", required = true) String action){
		if(action.equals("Cancelar")){
			return "redirect:/precondicion/";
		}
		if(result.hasErrors()) {
			model.addAttribute("autotransiciones", autoService.findAll());
			return "precondicion/precondicion-save";
		}else if (!action.equals("Cancelar")) {
			try {
				preService.save(precondicion);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error de lokos");
			}
		}else if (action.equals("Limpiar"))  {
			return "redirect:/precondicion/add";
		}
		return "redirect:/precondicion/";
	}
	
	//-----------------------Editar----------------------------
	
	@PreAuthorize("hasRole('Operador')")
	@GetMapping("/precondicion/edit/{id}")
	public String show(@PathVariable("id") long id, Model model) {
		Optional<Precondition> pre = preService.findPrecondition(id);
		if (pre == null)
			throw new IllegalArgumentException("Invalid appointment Id:" + id);
		model.addAttribute("precondicion", pre.get());
		model.addAttribute("autotransiciones", autoService.findAll());
		return "precondicion/precondicion-edit";
	}
	
	@PreAuthorize("hasRole('Operador')")
	@PostMapping("/precondicion/edit/{id}")
	public String update(@PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action,
			@Validated({ValidacionUno.class})  @ModelAttribute("precondicion") Precondition precondicion, BindingResult bindingResult, Model model) {
		if(action.equals("Cancelar")) {
			return "redirect:/precondicion/";
		}else if (bindingResult.hasErrors()) {			
			Optional<Precondition> prec= preService.findPrecondition(id);
			if (!prec.isPresent())
				throw new IllegalArgumentException("Invalid device type Id:" + id);
			model.addAttribute("autotransiciones", autoService.findAll());
			return "precondicion/precondicion-edit";
		}else if (!action.equals("Cancelar")) {
			try{
				Optional<Precondition> prec = preService.findPrecondition(id);
				prec.get().setPreconLogicaloperand(precondicion.getPreconLogicaloperand());
				prec.get().setAutotransition(precondicion.getAutotransition());
				preService.save(prec.get());
				model.addAttribute("precondicion", preService.findAll());
				return "redirect:/precondicion/";
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		return "redirect:/precondicion/";
	}
	
	//-----------------------Consultar----------------------------
	
	@PreAuthorize("hasRole('Operador')")
	@RequestMapping(value = "/view/precondicion/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String showPre(@PathVariable("id") long id, Model model) {
		Optional<Precondition> prep = preService.findPrecondition(id);
		if (prep == null)
			throw new IllegalArgumentException("Invalid appointment Id:" + id);
		model.addAttribute("precondicion", prep.get());
		return "precondicion/precondicion-view";
	}
	
	
}
