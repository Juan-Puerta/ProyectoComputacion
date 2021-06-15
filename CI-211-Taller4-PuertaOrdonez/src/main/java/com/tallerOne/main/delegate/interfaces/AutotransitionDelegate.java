package com.tallerOne.main.delegate.interfaces;

import java.util.List;

import com.tallerOne.main.model.Autotransition;

public interface AutotransitionDelegate {

	public List<Autotransition> getAutotransitions();
	public Autotransition getAutotransition(long autId);
	public Autotransition addAutotransition(Autotransition aut);
	public List<Autotransition> deleteAutotransition(long autId);
	public Autotransition editAutotransition(Autotransition aut);
	
}
