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

import com.tallerOne.main.controller.interfaces.LocController;
import com.tallerOne.main.model.Localcondition;
import com.tallerOne.main.services.LocalConditionService;
import com.tallerOne.main.services.PreconditionService;
import com.tallerOne.main.services.ThresholdService;
import com.tallerOne.main.validations.ValidacionUno;

@Controller
public class locControllerImp implements LocController{
	
	private LocalConditionService locService;
	private PreconditionService preService;
	private ThresholdService thrService;
	
	@Autowired
	public locControllerImp(LocalConditionService locService, PreconditionService preService,
			ThresholdService thrService) {
		super();
		this.locService = locService;
		this.preService = preService;
		this.thrService = thrService;
	}
	
	@PreAuthorize("hasRole('Administrador'||'Operador')")
	@RequestMapping(value = "/condicionlocal/", method = { RequestMethod.GET, RequestMethod.POST })
	@GetMapping("/condicionlocal/")
	public String locMain(Model model){
		model.addAttribute("condicionlocal", locService.findAll());
		return "condicionlocal/condicionlocal";
	}
	
	//-----------------------Guardar-------------------------
	
	@PreAuthorize("hasRole('Operador')")
	@GetMapping("/condicionlocal/add")
	public String addLloc(Model model){
		model.addAttribute("condicionlocal", new Localcondition());
		model.addAttribute("precondiciones", preService.findAll());
		model.addAttribute("thresholdes", thrService.findAll());
		return "condicionlocal/condicionlocal-save";
	}
	
	@PreAuthorize("hasRole('Operador')")
	@PostMapping("/condicionlocal/add")
	public String addLoc(@Validated({ValidacionUno.class}) @ModelAttribute("condicionlocal")Localcondition condicionlocal, BindingResult result, 
			Model model, @RequestParam(value = "action", required = true) String action){
		if(action.equals("Cancelar")){
			return "redirect:/condicionlocal/";
		}
		if(result.hasErrors()) {
			model.addAttribute("precondiciones", preService.findAll());
			model.addAttribute("thresholdes", thrService.findAll());
			return "condicionlocal/condicionlocal-save";
		}else if (!action.equals("Cancelar")) {
			try {
				locService.saveLocalCondition(condicionlocal);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}else if (action.equals("Limpiar"))  {
			return "redirect:/condicionlocal/add";
		}
		return "redirect:/condicionlocal/";
	}
	
	//-----------------------Editar-------------------------
	
	@PreAuthorize("hasRole('Operador')")
	@GetMapping("/condicionlocal/edit/{id}")
	public String showUpdate(@PathVariable("id") long id, Model model) {
		Optional<Localcondition> loc = locService.findLocalcondition(id);
		if (loc == null)
			throw new IllegalArgumentException("Invalid appointment Id:" + id);
		model.addAttribute("condicionlocal", loc.get());
		model.addAttribute("precondiciones", preService.findAll());
		model.addAttribute("thresholdes", thrService.findAll());
		return "condicionlocal/condicionlocal-edit";
	}
	
	@PreAuthorize("hasRole('Operador')")
	@PostMapping("/condicionlocal/edit/{id}")
	public String update(@PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action,
			@Validated({ValidacionUno.class})  @ModelAttribute("condicionlocal") Localcondition condicionlocal, BindingResult bindingResult, Model model) {
		if(action.equals("Cancelar")) {
			return "redirect:/condicionlocal/";
		}else if (bindingResult.hasErrors()) {			
			Optional<Localcondition> loc= locService.findLocalcondition(id);
			if (!loc.isPresent())
				throw new IllegalArgumentException("Invalid device type Id:" + id);
			model.addAttribute("precondiciones", preService.findAll());
			model.addAttribute("thresholdes", thrService.findAll());
			return "condicionlocal/condicionlocal-edit";
		}else if (!action.equals("Cancelar")) {
			try{
				Optional<Localcondition> loc = locService.findLocalcondition(id);
				loc.get().setLoconColumnname(condicionlocal.getLoconColumnname());
				loc.get().setLoconKeycolumn(condicionlocal.getLoconKeycolumn());
				loc.get().setLoconOperator(condicionlocal.getLoconOperator());
				loc.get().setLoconQuerystring(condicionlocal.getLoconQuerystring());
				loc.get().setLoconTablename(condicionlocal.getLoconTablename());
				loc.get().setLoconValuetype(condicionlocal.getLoconValuetype());
				loc.get().setPrecondition(condicionlocal.getPrecondition());
				loc.get().setThreshold(condicionlocal.getThreshold());
				locService.saveLocalCondition(loc.get());
				model.addAttribute("condicionlocal", locService.findAll());
				return "redirect:/condicionlocal/";
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		return "redirect:/condicionlocal/";
	}
	
	//-----------------------Consultar-------------------------
	
	@PreAuthorize("hasRole('Operador')")
	@RequestMapping(value = "/view/condicionlocal/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String show(@PathVariable("id") long id, Model model) {
		Optional<Localcondition> loc = locService.findLocalcondition(id);
		if (loc == null)
			throw new IllegalArgumentException("Invalid appointment Id:" + id);
		model.addAttribute("condicionlocal", loc.get());
		return "condicionlocal/condicionlocal-view";
	}
	
}
