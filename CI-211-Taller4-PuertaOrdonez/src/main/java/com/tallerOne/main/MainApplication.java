package com.tallerOne.main;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import com.tallerOne.main.model.Person;
import com.tallerOne.main.model.PersonRole;
import com.tallerOne.main.model.PersonRolePK;
import com.tallerOne.main.model.Rolee;
import com.tallerOne.main.model.Userr;
import com.tallerOne.main.repositories.PersonRepository;
import com.tallerOne.main.repositories.PersonRoleRepository;
import com.tallerOne.main.repositories.RoleeRepository;
import com.tallerOne.main.services.UserrService;

@SpringBootApplication
public class MainApplication {

	@Autowired
	private UserrService userService;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private RoleeRepository rolRepository;
	
	@Autowired
	private PersonRoleRepository perolRepository;
	
	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}
	
	@Bean
	@Transactional
	public CommandLineRunner dummy() {
		return (args) -> {
			
			//Creación del primer usuario
			Person per1=new Person();
			List<PersonRole> roles1=new ArrayList<>();
			
			Rolee rol1=new Rolee();
			rol1.setRoleName("Administrador");
			
			rolRepository.save(rol1);
			personRepository.save(per1);
			
			PersonRolePK pk1=new PersonRolePK();
			pk1.setPersPersId(per1.getPersId());
			pk1.setRoleRoleId(rol1.getRoleId());
			

			PersonRole pr1=new PersonRole();
			pr1.setRolee(rol1);
			pr1.setPerson(per1);
			pr1.setId(pk1);
			
			roles1.add(pr1);
			
			Userr user1=new Userr();
			user1.setUserName("usuario-administrador");
			user1.setUserPassword("{noop}123");
			user1.setPerson(per1);
			
			perolRepository.save(pr1);
			userService.saveUser(user1);			

			Userr userEd=new Userr();
			userEd.setUserId(userService.findUserr(user1).get().getUserId());
			userEd.setPerson(userService.findUserr(user1).get().getPerson());
			userEd.setUserName(userService.findUserr(user1).get().getUserName());
			userEd.setUserPassword(userService.findUserr(user1).get().getUserPassword());
			userEd.getPerson().setPersonRoles(roles1);
			
			userService.editUserr(userEd);
			
			//Creación del segundo usuario
			Person p2=new Person();
			List<PersonRole> roles2=new ArrayList<>();
			
			Rolee rol2=new Rolee();
			rol2.setRoleName("Operador");
			
			rolRepository.save(rol2);
			personRepository.save(p2);
			
			PersonRolePK pk2=new PersonRolePK();
			pk2.setPersPersId(p2.getPersId());
			pk2.setRoleRoleId(rol2.getRoleId());
			
			PersonRole pr2=new PersonRole();
			pr2.setRolee(rol2);
			pr2.setPerson(p2);
			pr2.setId(pk2);
			
			roles2.add(pr2);

			Userr user2=new Userr();
			user2.setUserName("usuario-operador");
			user2.setUserPassword("{noop}123");
			user2.setPerson(p2);
			
			perolRepository.save(pr2);
			userService.saveUser(user2);
			
			Userr userEd2=new Userr();
			userEd2.setUserId(userService.findUserr(user2).get().getUserId());
			userEd2.setPerson(userService.findUserr(user2).get().getPerson());
			userEd2.setUserName(userService.findUserr(user2).get().getUserName());
			userEd2.setUserPassword(userService.findUserr(user2).get().getUserPassword());
			userEd2.getPerson().setPersonRoles(roles2);
			
			userService.editUserr(userEd2);
			
		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}
