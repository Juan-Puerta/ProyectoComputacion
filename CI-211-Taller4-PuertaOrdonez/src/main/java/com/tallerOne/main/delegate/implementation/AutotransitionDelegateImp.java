package com.tallerOne.main.delegate.implementation;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.tallerOne.main.delegate.interfaces.AutotransitionDelegate;
import com.tallerOne.main.model.Autotransition;

@Component
public class AutotransitionDelegateImp implements AutotransitionDelegate{

	@Autowired
	RestTemplate template;
	
	final String SERVER = "http://localhost:8080";
	
	@Override
	public List<Autotransition> getAutotransitions() {
		// TODO Auto-generated method stub
		String request=String.format("%s/view/autos/",SERVER);
		Autotransition[] autos=template.getForObject(request, Autotransition[].class);
		List<Autotransition> auts=Arrays.asList(autos);
		return auts;
	}

	@Override
	public Autotransition getAutotransition(long autId) {
		// TODO Auto-generated method stub
		String request=String.format("%s/view/autos/%o", SERVER, autId);
		Autotransition aut = template.getForObject(request, Autotransition.class);
		return aut;
	}

	@Override
	public Autotransition addAutotransition(Autotransition aut) {
		// TODO Auto-generated method stub
		String request=String.format("%s/add/auto/",SERVER);
		template.postForObject(request,aut, Autotransition.class);
		return aut;
	}

	@Override
	public List<Autotransition> deleteAutotransition(long autId) {
		// TODO Auto-generated method stub
		String request=String.format("%s/delete/auto/%o",SERVER, autId);
		template.delete(request);
		return this.getAutotransitions();
	}

	@Override
	public Autotransition editAutotransition(Autotransition aut) {
		// TODO Auto-generated method stub
		String request=String.format("%s/autos/edit",SERVER);
		template.put(request, aut, Autotransition.class);
		return this.getAutotransition(aut.getAutotranId());
	}

}
