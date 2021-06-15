package com.tallerOne.main.rest.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.tallerOne.main.model.Autotransition;
import com.tallerOne.main.rest.controller.interfaces.AutoRestController;
import com.tallerOne.main.services.AutotransitionServiceImp;

@RestController
public class AutoRestControllerImp implements AutoRestController{

	@Autowired
	AutotransitionServiceImp autServ;
	
	@Override
	@GetMapping("/view/autos")
	public Iterable<Autotransition> getAutotransitions() {
		// TODO Auto-generated method stub
		return autServ.findAll();
	}

	@Override
	@GetMapping("/view/autos/{autId}")
	public Autotransition getAutotransition(@PathVariable long autId) {
		// TODO Auto-generated method stub
		try {
			if(autServ.findAutotransition(autId).isPresent()) {
				return autServ.findAutotransition(autId).get();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@PostMapping("/add/auto")
	public void addAutotransition(@RequestBody Autotransition aut) {
		// TODO Auto-generated method stub
		try{
			autServ.save(aut);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@DeleteMapping("/delete/auto/{autId}")
	public void deleteAutotransition(@PathVariable long autId) {
		// TODO Auto-generated method stub
		try{
			autServ.deleteAuto(autId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	@PutMapping("/autos/edit")
	public void editAutotransition(@RequestBody Autotransition aut) {
		// TODO Auto-generated method stub
		try{
			autServ.editAutotransition(aut);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
