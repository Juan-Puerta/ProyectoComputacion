package com.tallerOne.main.delegate.interfaces;

import java.util.List;

import com.tallerOne.main.model.Precondition;

public interface PreconditionDelegate {

	public List<Precondition> getPreconditions();
	public Precondition getPrecondition(long preId);
	public Precondition addPrecondition(Precondition pre);
	public List<Precondition> deletePrecondition(long preId);
	public Precondition editPrecondition(Precondition pre);
	
}
