package com.tallerOne.main.delegate.implementation;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.tallerOne.main.delegate.interfaces.LocalConditionDelegate;
import com.tallerOne.main.model.Localcondition;

@Component
public class LocalConditionDelegateImp implements LocalConditionDelegate{

	@Autowired
	RestTemplate template;
	
	final String SERVER = "http://localhost:8080";
	
	@Override
	public List<Localcondition> getLocalconditions() {
		// TODO Auto-generated method stub
		String request=String.format("%s/view/locals/",SERVER);
		Localcondition[] locals=template.getForObject(request, Localcondition[].class);
		List<Localcondition> locs=Arrays.asList(locals);
		return locs;
	}

	@Override
	public Localcondition getLocalcondition(long locId) {
		// TODO Auto-generated method stub
		String request=String.format("%s/view/local/%o", SERVER, locId);
		Localcondition loc = template.getForObject(request, Localcondition.class);
		return loc;
	}

	@Override
	public Localcondition addLocalcondition(Localcondition loc) {
		// TODO Auto-generated method stub
		String request=String.format("%s/add/local",SERVER);
		template.postForObject(request, loc, Localcondition.class);
		return loc;
	}

	@Override
	public List<Localcondition> deleteLocalcondition(long locId) {
		// TODO Auto-generated method stub
		String request=String.format("%s/delete/local/%o",SERVER, locId);
		template.delete(request);
		return this.getLocalconditions();
	}

	@Override
	public Localcondition editLocalcondition(Localcondition loc) {
		// TODO Auto-generated method stub
		String request=String.format("%s/edit/local",SERVER);
		template.put(request, loc, Localcondition.class);
		return loc;
	}

}
