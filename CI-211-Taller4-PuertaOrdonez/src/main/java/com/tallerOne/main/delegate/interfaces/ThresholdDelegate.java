package com.tallerOne.main.delegate.interfaces;

import java.util.List;

import com.tallerOne.main.model.Threshold;

public interface ThresholdDelegate {

	public List<Threshold> getThresholds();
	public Threshold getThreshold(long thrId);
	public Threshold addThreshold(Threshold thr);
	public List<Threshold> deleteThreshold(long thrId);
	public Threshold editThreshold(Threshold thr);
	
}
