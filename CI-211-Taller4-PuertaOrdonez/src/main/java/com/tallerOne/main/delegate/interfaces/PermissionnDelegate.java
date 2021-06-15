package com.tallerOne.main.delegate.interfaces;

import java.util.List;

import com.tallerOne.main.model.Permissionn;

public interface PermissionnDelegate {

	public List<Permissionn> getPermissionns();
	public Permissionn getPermissionn(long perId);
	public Permissionn addPermissionn(Permissionn per);
	public List<Permissionn> deletePermissionn(long perId);
	public Permissionn editPermissionn(Permissionn per);
	
}
