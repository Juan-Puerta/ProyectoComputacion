package com.tallerOne.main.rest.controller.implementation;

import org.springframework.web.bind.annotation.RestController;

import com.tallerOne.main.model.Permissionn;
import com.tallerOne.main.rest.controller.interfaces.PermissionnRestController;

@RestController
public class PermissionnRestControllerImp implements PermissionnRestController{

	
	
	@Override
	public Iterable<Permissionn> getPermissionns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permissionn getPermissionn(long perId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPermissionn(Permissionn per) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePermissionn(long perId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editPermissionn(Permissionn per) {
		// TODO Auto-generated method stub
		
	}

}
