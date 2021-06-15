package com.tallerOne.main.rest.controller.implementation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tallerOne.main.model.Localcondition;
import com.tallerOne.main.rest.controller.interfaces.LocalRestController;
import com.tallerOne.main.services.LocalConditionServiceImp;

@RestController
public class LocalRestControllerImp implements LocalRestController{

	LocalConditionServiceImp locServ;
	
	@Override
	@GetMapping("/view/locals")
	public Iterable<Localcondition> getLocalconditions() {
		// TODO Auto-generated method stub
		return locServ.findAll();
	}

	@Override
	@GetMapping("/view/local/{locId}")
	public Localcondition getLocalcondition(@PathVariable long locId) {
		// TODO Auto-generated method stub
		try {
			if(locServ.findLocalcondition(locId).isPresent()) {
				return locServ.findLocalcondition(locId).get();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@PostMapping("/add/local")
	public void addLocalcondition(@RequestBody Localcondition loc) {
		// TODO Auto-generated method stub
		try{
			locServ.save(loc);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@DeleteMapping("/delete/local/{locId}")
	public void deleteLocalcondition(@PathVariable Localcondition locId) {
		// TODO Auto-generated method stub
		try{
			locServ.deleteLocalcondition(locId);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@PutMapping("/edit/local")
	public void editLocalcondition(@RequestBody Localcondition loc) {
		// TODO Auto-generated method stub
		try{
			locServ.editLocalCondition(loc);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
