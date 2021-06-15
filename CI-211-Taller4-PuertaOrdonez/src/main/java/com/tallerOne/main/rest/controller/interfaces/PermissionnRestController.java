package com.tallerOne.main.rest.controller.interfaces;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.tallerOne.main.model.Permissionn;

public interface PermissionnRestController {

	public Iterable<Permissionn> getPermissionns();
	public Permissionn getPermissionn(@PathVariable long perId);
	public void addPermissionn(@RequestBody Permissionn per);
	public void deletePermissionn(@PathVariable long perId);
	public void editPermissionn(@RequestBody Permissionn per);
	
}
