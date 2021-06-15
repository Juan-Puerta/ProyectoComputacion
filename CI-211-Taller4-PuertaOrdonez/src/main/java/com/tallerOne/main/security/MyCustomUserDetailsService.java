package com.tallerOne.main.security;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tallerOne.main.model.Userr;
import com.tallerOne.main.repositories.UserrRepository;


@Service
public class MyCustomUserDetailsService implements UserDetailsService {
	
	private UserrRepository userRep;
	
	@Autowired
	public MyCustomUserDetailsService(UserrRepository userRep) {
		this.userRep = userRep;
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Userr userApp = userRep.findByUserName(username).get(0);
		if (userApp != null) {
			//System.out.println(username);
			User.UserBuilder builder = User.withUsername(username).password(userApp.getUserPassword()).
					roles(userApp.getPerson().getPersonRoles().get(0).getRolee().getRoleName());
			return builder.build();
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
	}
}