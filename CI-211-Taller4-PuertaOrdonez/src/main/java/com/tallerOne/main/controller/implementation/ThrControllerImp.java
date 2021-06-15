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

import com.tallerOne.main.controller.interfaces.ThrController;
import com.tallerOne.main.model.Threshold;
import com.tallerOne.main.services.InstitutionService;
import com.tallerOne.main.services.ThresholdService;
import com.tallerOne.main.validations.ValidacionDos;
import com.tallerOne.main.validations.ValidacionUno;

@Controller
public class ThrControllerImp implements ThrController{

	private ThresholdService thrService;
	private InstitutionService insService;
	
	@Autowired
	public ThrControllerImp(ThresholdService thrService, InstitutionService insService) {
		super();
		this.thrService = thrService;
		this.insService = insService;
	}
	
	@PreAuthorize("hasRole('Administrador'||'Operador')")
	@RequestMapping(value = "/threshold/", method = { RequestMethod.GET, RequestMethod.POST })
	@GetMapping("/threshold/")
	public String thresholdMain(Model model){
		model.addAttribute("threshold", thrService.findAll());
		return "threshold/threshold";
	}
	
	//----------------Guardar------------------------------
	
	@PreAuthorize("hasRole('Operador')")
	@GetMapping("/threshold/add")
	public String addThr(Model model){
		model.addAttribute("threshold", new Threshold());
		model.addAttribute("instituciones", insService.findAll());
		return "threshold/threshold-save";
	}
	
	@PreAuthorize("hasRole('Operador')")
	@PostMapping("/threshold/add")
	public String addThr(@Validated({ValidacionUno.class, ValidacionDos.class}) @ModelAttribute("threshold")Threshold thr, BindingResult result, 
			Model model, @RequestParam(value = "action", required = true) String action){
		if(action.equals("Cancelar")){
			return "redirect:/threshold/";
		}
		if(result.hasErrors()) {
			model.addAttribute("instituciones", insService.findAll());
			return "threshold/threshold-save";
		}else if (!action.equals("Cancelar")) {
			try {
				thrService.save(thr);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}else if (action.equals("Limpiar"))  {
			return "redirect:/threshold/add";
		}
		return "redirect:/threshold/";
	}
	
	//---------------------------Editar-----------------------------
	
	@PreAuthorize("hasRole('Operador')")
	@GetMapping("/threshold/edit/{id}")
	public String showUpdate(@PathVariable("id") long id, Model model) {
		Optional<Threshold> thre = thrService.findThreshold(id);
		if (thre == null)
			throw new IllegalArgumentException("Invalid appointment Id:" + id);
		model.addAttribute("threshold", thre.get());
		model.addAttribute("instituciones", insService.findAll());
		return "threshold/threshold-edit";
	}
	
	@PreAuthorize("hasRole('Operador')")
	@PostMapping("/threshold/edit/{id}")
	public String update(@PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action,
			@Validated({ValidacionUno.class, ValidacionDos.class})  @ModelAttribute("threshold") Threshold threshold, BindingResult bindingResult, Model model) {
		if(action.equals("Cancelar")) {
			return "redirect:/threshold/";
		}else if (bindingResult.hasErrors()) {			
			Optional<Threshold> thre= thrService.findThreshold(id);
			if (!thre.isPresent())
				throw new IllegalArgumentException("Invalid device type Id:" + id);
			model.addAttribute("instituciones", insService.findAll());
			return "threshold/threshold-edit";
		}else if (!action.equals("Cancelar")) {
			try{
				Optional<Threshold> thre = thrService.findThreshold(id);
				thre.get().setThresName(threshold.getThresName());
				thre.get().setThresValue(threshold.getThresValue());
				thre.get().setThresValuetype(threshold.getThresValuetype());
				thre.get().setInstInstId(threshold.getInstInstId());
				thrService.saveThreshold(thre.get());
				model.addAttribute("threshold", thrService.findAll());
				return "redirect:/threshold/";
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		return "redirect:/threshold/";
	}
	
	//-------------------Consultar----------------------------
	
	@PreAuthorize("hasRole('Operador')")
	@RequestMapping(value = "/view/threshold/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String show(@PathVariable("id") long id, Model model) {
		Optional<Threshold> thre = thrService.findThreshold(id);
		if (thre == null)
			throw new IllegalArgumentException("Invalid appointment Id:" + id);
		model.addAttribute("threshold", thre.get());
		return "threshold/threshold-view";
	}
	
}
